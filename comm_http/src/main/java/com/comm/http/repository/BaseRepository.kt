package com.comm.http.repository


import android.util.Log
import com.comm.http.RequestCreator

import com.comm.http.exception.RetrofitException
import com.comm.http.model.BaseRespModel
import com.comm.http.model.NetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope


open class BaseRepository {

    val mRequestClient = RequestCreator.instance

    suspend fun <T : Any> callRequest(
        call: suspend () -> NetResult<T>
    ): NetResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            //这里统一处理异常
            Log.e("BaseRepository", "callRequest: ${e.message}")
            NetResult.Error(RetrofitException.retrofitException(e))
        }
    }


    suspend fun <T : Any> handleResponse(
        response: T,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): NetResult<T> {
    return coroutineScope{
            Log.e("BaseRepository", "handleResponse: ${response}")
//            if (response.code != 200) {
//                errorBlock?.let {
//                    it()
//                }
////                Log.e("BaseRepository", "handleResponse: ${response.msg}" )
//                NetResult.Error(
//                    RetrofitException.ResponeThrowable(
//                        Throwable(response.msg),
//                        response.code
//                    )
//                )
//
//            } else {
                successBlock?.let {
                    it()
                }
                NetResult.Success(response)
//            }
        }
    }


    /**
     * 去掉协程的实现方式
     */
    fun <T : Any> handleResp(
        response: BaseRespModel<T>,
        successCall: () -> Unit,
        errorCall: () -> Unit
    ): NetResult<T> {
        return if (response.code != 0) {
            successCall()
            NetResult.Error(
                RetrofitException.ResponeThrowable(
                    Throwable(response.msg),
                    response.code
                )
            )
        } else {
            errorCall()
            NetResult.Success(response.data)
        }
    }

}


