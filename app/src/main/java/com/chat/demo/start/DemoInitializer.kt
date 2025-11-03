package com.chat.demo.start

import android.content.Context
import androidx.startup.Initializer
import com.chat.demo.net.RequestConfigImpl
import com.comm.base.utils.LogUtils
import com.comm.http.RequestCreator

/**
 * Created By qiaohao on 2025/5/23 10:02
 * desc: 为每个需要在application中初始化的操作写个initializer
 * 整个初始化过程是一个有向无环图
 * 注意：需要在清单文件中配置 mate-data指定该类
 * https://developer.android.google.cn/topic/libraries/app-startup?hl=en
 */
class DemoInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        //初始化网络请求拦截器
        RequestCreator.instance.initConfig(RequestConfigImpl())
        LogUtils.instance.e("DemoInitializer",{"DemoInitializer create"})
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        //配置依赖关系，没有依赖贼返回空数据
      return mutableListOf()
    }
}