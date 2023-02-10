package com.comm.http.live

import androidx.lifecycle.LiveData
import com.mvvm.kt.entity.BaseResp

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.IllegalArgumentException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *@author qiaohao
 *@date 21-4-7 上午10:16
 * @desc 自定义liveata calladapter factory
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java){
            return null
        }
        var observableType = getParameterUpperBound(0,returnType as ParameterizedType)
        var rawType = getRawType(observableType)
        var isApiResponse = true
        if (rawType != BaseResp::class.java){
            isApiResponse = false
        }
        if (observableType is ParameterizedType){
            throw IllegalArgumentException("resource must be parameterized")
        }
        return LiveDataCallAdapter<BaseResp>(observableType,isApiResponse)
    }
}