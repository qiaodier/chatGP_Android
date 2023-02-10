package com.comm.base.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *@author qiaohao
 *@date 21-4-2 上午10:25
 *
 */
abstract class BaseViewModel:ViewModel() {

    val errorLive: MutableLiveData<String> = MutableLiveData()

//    init {
//        initRepository()
//    }
//
//    abstract fun initRepository()
}