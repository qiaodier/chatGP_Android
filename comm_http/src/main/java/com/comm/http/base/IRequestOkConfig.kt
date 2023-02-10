package com.comm.http.base

import okhttp3.Interceptor

interface IRequestOkConfig {

    /**
     * 返回使用方传入的baseurl
     */
    fun baseUrl():String

    /**
     * 返回使用者自定义的拦截器
      */
    fun interceptorList():List<Interceptor>?

    /**
     * 编译环境
     */
    fun isDebug():Boolean

}