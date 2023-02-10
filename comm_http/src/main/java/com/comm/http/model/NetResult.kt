package com.comm.http.model


import com.comm.http.exception.RetrofitException


sealed class NetResult<out T : Any> {

    data class Success<out T : Any>(val data: T?) : NetResult<T>()

    data class Error(val exception: RetrofitException.ResponeThrowable) : NetResult<Nothing>(){
        init {
//            RequestCreator.instance.getError().postValue(exception.message)
        }
    }



}