package com.comm.http.repository

import android.util.Log
import com.comm.http.RequestCreator
import com.comm.http.exception.RetrofitException
import com.comm.http.model.BaseRespModel
import com.comm.http.model.NetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author iqiao
 * @date 2021-11-30 0030 15:53:33
 * @desc
 */

open class BaseFlowRepository {

    val mRequestClient = RequestCreator.instance

    suspend fun <T : Any> callRequest(
        call: suspend () -> Flow<NetResult<T>>
    ): Flow<NetResult<T>> {
        return flow {
            try {
                call()
            } catch (e: Exception) {
                //这里统一处理异常
                Log.e("BaseRepository", "callRequest: ${e.message}")
//            NetResult.error.postValue(e.message)
                emit(NetResult.Error(RetrofitException.retrofitException(e)))
            }
        }
    }

    /**
     * 此实现方式需要在api接口函数中加suspend
     */
    suspend fun <T : Any> handleResponse(
        response: BaseRespModel<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): Flow<NetResult<T>> {
        return flow {
            coroutineScope {
                if (response.code != 0) {
                    errorBlock?.let {
                        it()
                    }
//                Log.e("BaseRepository", "handleResponse: ${response.message}" )
                    emit(NetResult.Error(
                        RetrofitException.ResponeThrowable(
                            Throwable(response.msg),
                            response.code
                        )
                    ))

                } else {
                    successBlock?.let {
                        it()
                    }
                    emit(NetResult.Success(response.data))
                }
            }
        }
    }
    /**
     * 此实现方式需要在api接口函数中不加suspend
     */
    fun <T : Any> handleResp(
        response: BaseRespModel<T>,
        successCall: (() -> Unit)?=null,
        errorCall: (() -> Unit)?=null
    ): Flow<NetResult<T>> {
        return flow {
            if (response.code != 0) {
                successCall?.let {
                    it()
                }
                NetResult.Error(
                    RetrofitException.ResponeThrowable(
                        Throwable(response.msg),
                        response.code
                    )
                )
            } else {
                errorCall?.let {
                    it()
                }
                NetResult.Success(response.data)
            }
        }
    }
}