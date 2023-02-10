package com.chat.demo.utils

import android.util.Log
import com.orhanobut.logger.LogStrategy

/**
 * @author iqiao
 * desc: 处理android studio 3.1 Logcat 日志合并TAG问题
 */
class CustomLogCatStrategy : LogStrategy {
    override fun log(
        priority: Int,
        tag: String?,
        message: String
    ) {
        Log.println(priority, randomKey() + tag, message)
        //        com.tencent.mars.xlog.Log.e(randomKey() + tag, message);
    }

    private var last = 0
    private fun randomKey(): String {
        var random = (10 * Math.random()).toInt()
        if (random == last) {
            random = (random + 1) % 10
        }
        last = random
        return random.toString()
    }
}