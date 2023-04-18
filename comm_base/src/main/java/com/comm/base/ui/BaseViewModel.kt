package com.comm.base.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.comm.base.lifecycle.BaseLifecycleObserver

/**
 *@author qiaohao
 *@date 21-4-2 上午10:25
 *
 */
abstract class BaseViewModel:ViewModel(), BaseLifecycleObserver {

    val TAG = this.javaClass.name

    // 子类的viewmodel中可以选择实现某个生命周期回调方法
    override fun onCreate() {}

    override fun onStart() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onStop() {}

    override fun onDestroy() {}

    override fun onAny() {}
}