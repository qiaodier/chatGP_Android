package com.comm.base.utils

import android.util.Log



/**
 * @author iqiao
 * @date 2021-12-31 0031 09:45:28
 * @desc log工具类
 */
class LogUtils {

    /**
     * 静态内部类实现单例
     */
    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = LogUtils()
    }


    var logEnable = true


    fun d(tag:String,content:()->String){
        if(logEnable){
            Log.d(tag,content())
        }
    }

    fun  e(tag:String,content:()->String){
        if(logEnable){
            Log.e(tag,content())
        }
    }

    fun i(tag:String,content:()->String){
        if(logEnable){
            Log.i(tag,content())
        }
    }

    fun w(tag:String,content:()->String){
        if(logEnable){
            Log.w(tag,content())
        }
    }

    fun v(tag:String,content:()->String){
        if(logEnable){
            Log.v(tag,content())
        }
    }

}