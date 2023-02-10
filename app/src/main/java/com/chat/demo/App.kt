package com.chat.demo

import android.app.Application
import com.chat.demo.net.RequestConfigImpl
import com.chat.demo.utils.CustomLogCatStrategy
import com.comm.http.RequestCreator
import com.hjq.toast.ToastUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        //初始化网络请求拦截器
        RequestCreator.instance.initConfig(RequestConfigImpl())
        //吐司初始化
        ToastUtils.init(this)
    }

    private fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(2)
            .methodOffset(5)
            .logStrategy(CustomLogCatStrategy())
            .tag("-request-log")
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

}