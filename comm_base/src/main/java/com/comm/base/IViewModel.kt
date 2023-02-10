package com.comm.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference
import java.util.*

/**
 *@author qiaohao
 *@date 21-4-2 上午10:24
 *@desc 继承ViewModel
 */
abstract class IViewModel:ViewModel() {

    init {
        initModel()
    }

    abstract fun initModel()

}