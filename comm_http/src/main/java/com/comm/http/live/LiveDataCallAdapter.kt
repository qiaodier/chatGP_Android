package com.comm.http.live


import android.util.Log
import androidx.lifecycle.LiveData
import com.comm.http.RequestCreator
import com.comm.http.exception.NetworkError
import com.comm.http.framework.RequestManager
import com.google.gson.JsonParseException
import com.hjq.toast.ToastUtils
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.mvvm.kt.entity.BaseResp
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 *@author qiaohao
 *@date 21-4-7 上午9:48
 *@desc 实现了livedata的calladapter
 */
class LiveDataCallAdapter<T>(private val responseType:Type,private val isApiResponse:Boolean ) : CallAdapter<T,LiveData<T>> {



    override fun adapt(call: Call<T>): LiveData<T> {
        return CustomLiveData<T>(call,isApiResponse)
    }

    override fun responseType() = responseType

    companion object class CustomLiveData<T>(private  val call:Call<T>,private val isApiResponse: Boolean) : LiveData<T>(){
        private val start = AtomicBoolean(false)
        override fun onActive() {
            super.onActive()
            if (start.compareAndSet(false,true)){
                RequestManager.getInstance().addTask {
                    Log.e("LiveDataCallAdapter", "onActive: ${Thread.currentThread().name} url:${call.request().url()}" )
//                    RequestCreator.instance.getLoadingStatus().postValue(true)
                    call.enqueue(object :Callback<T>{
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            NetworkError.error(t)
//                            RequestCreator.instance.getLoadingStatus().postValue(false)
                        }
                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            val body = response.body()
                            val resp:BaseResp? = body as? BaseResp
                            if (resp==null){
                                Log.e("LiveDataCallAdapter", "onResponse: resp==null", )
                                when(response.code()){
                                    404 ->{
                                        NetworkError.error(HttpException(response))
                                    }
                                    else ->{
                                        NetworkError.error(JsonParseException("服务响应内容为空 ${response.code()}"))
                                    }
                                }
                            }else{
//                                Log.e("LiveDataCallAdapter", "onResponse: $body", )
                                postValue(body)
                            }
//                            RequestCreator.instance.getLoadingStatus().postValue(false)
                        }

                    })
                }
            }
        }
    }
}