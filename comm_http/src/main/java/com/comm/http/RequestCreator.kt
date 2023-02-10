package com.comm.http

import androidx.lifecycle.MutableLiveData
import com.comm.http.base.IRequestOkConfig
import com.comm.http.live.LiveDataCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *@author qiaohao
 *@date 21-4-2 下午1:49
 */
class RequestCreator {

    /**
     * 执行在构造器之前
     */
    init { }

    private val CONNECT_TIMEOUT = 60;
    private val READ_TIMEOUT = 60;
    private lateinit var mRetrofit: Retrofit
    private lateinit var okHttpClient:OkHttpClient
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var iRequestOkConfig: IRequestOkConfig
    /**
     * 静态内部类实现单例
     */
    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = RequestCreator()
    }

    /**
     * 获取loading 状态
     */
    fun getLoadingStatus():MutableLiveData<Boolean>{
        return isLoading
    }

    /**
     * 传入使用者传入的config实现类
     * 需要在
     */
    fun initConfig(iRequestOkConfig: IRequestOkConfig){
        this.iRequestOkConfig = iRequestOkConfig
        okHttpClient = initOk()
    }

    /**
     * 构建请求真实对象
     */
    fun <T> get(service: Class<T>): T {
        return mRetrofit.create(service)
    }


    fun getOkClient():OkHttpClient{
        return okHttpClient;
    }

    /**
     * 初始化okHttp+Retrofit
     */
    private fun initOk() :OkHttpClient {
        var httpLog = HttpLoggingInterceptor(HttpLog())
        httpLog.level = if (iRequestOkConfig.isDebug())
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addNetworkInterceptor(httpLog)
            .hostnameVerifier { s, sslSession ->
                return@hostnameVerifier s.equals("api.openai.com")
            }
        val interceptorList = iRequestOkConfig.interceptorList()
        //使用者自定义的拦截器
        interceptorList?.forEach{
            okHttpClient.addInterceptor(it)
        }
        val client = okHttpClient.build()
        mRetrofit = Retrofit.Builder()
            .baseUrl(iRequestOkConfig.baseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            //使用rxjava处理网络响应数据
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //使用livedata处理网络响应数据
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()

        return client
    }

}