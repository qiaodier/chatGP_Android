package com.mvvm.kt.entity

/**
 *@author qiaohao
 *@date 21-4-2 上午11:21
 */
open class BaseResp {

    var code:Int =0
    var msg:String=""

    fun isOk():Boolean{
        return code == 200
    }
}