package com.comm.http.model

/**
 *@author qiaohao
 *@date 21-4-2 上午11:21
 */
data class BaseRespModel<out T>(val code: Int, val msg: String, val data: T?)