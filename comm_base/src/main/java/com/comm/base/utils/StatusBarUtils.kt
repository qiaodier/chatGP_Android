package com.comm.base.utils

import android.content.Context

object StatusBarUtils {
    /**
     * 获取状态栏高度
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else{
            0
        }
    }
}