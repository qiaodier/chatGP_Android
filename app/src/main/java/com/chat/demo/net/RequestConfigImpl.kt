package com.chat.demo.net

import com.comm.http.base.IRequestOkConfig

import okhttp3.Interceptor

/**
 * 配置网络请求实现类
 */
class RequestConfigImpl :IRequestOkConfig{

    companion object{
        var BASE_URL = "https://api.openai.com/"
    }

    override fun baseUrl() = BASE_URL

    override fun interceptorList(): List<Interceptor>? {
        val list:MutableList<Interceptor> = ArrayList()
        //公用请求头的拦截器
//        list.add(RequestHeaderInterceptor())
//        //token失效拦截器
//        list.add(TokenInvalidInterceptor())
        return list
    }

    override fun isDebug() = true
}