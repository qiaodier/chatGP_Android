package com.comm.http.model


import com.comm.http.exception.RetrofitException


sealed class NetResult<out T : Any> {

    data class Success<out T : Any>(val data: T?) : NetResult<T>()

    data class Error(val exception: RetrofitException.ResponeThrowable) : NetResult<Nothing>()


    //使用lambda方式拿到success的data内容
    fun onSuccess(onSuccess:(T?)-> Unit):NetResult<T>{
        if (this is Success){
            onSuccess.invoke(data)
        }
        return this
    }
    //使用lambda方式拿到onError的data内容
    fun onError(onError:(RetrofitException.ResponeThrowable)->Unit):NetResult<T>{
        if (this is Error){
            onError.invoke(exception)
        }
        return this
    }



}