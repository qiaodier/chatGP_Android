package com.comm.base.ui

import android.app.Dialog
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 防止内存泄露
 */
open class BaseDialog(context: Context, id: Int) : Dialog(context, id), LifecycleObserver {

    init {
        if (context is ComponentActivity) {
            context.lifecycle.addObserver(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        if (context is ComponentActivity) {
            (context as ComponentActivity).lifecycle.removeObserver(this)
        }
        if (isShowing) {
            dismiss()
        }
    }
}