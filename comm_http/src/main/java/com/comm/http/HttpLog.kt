package com.comm.http


import com.comm.http.utils.JsonUtil
import com.orhanobut.logger.Logger
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @author qiaohao
 * @date 21-4-2 下午2:14
 */
class HttpLog : HttpLoggingInterceptor.Logger {


    private val POST_FLAG = "--> POST"
    private val GET_FLAG = "--> GET"
    private val END_FLAG = "<-- END HTTP"
    private var mLogMessage: StringBuffer = StringBuffer()

    override fun log(message1: String) {
        var message = message1
        //请求或者响应开始
        if (message.startsWith(POST_FLAG) || message.startsWith(GET_FLAG)) {
            mLogMessage.setLength(0)
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if (message.startsWith("{") && message.endsWith("}")
            || message.startsWith("[") && message.endsWith("]")
        ) {
            message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message))
        }
        mLogMessage.append("${message}\n")
        // 请求或者响应结束，打印整条日志
        if (message.startsWith(END_FLAG)) {
            Logger.e(mLogMessage.toString())
        }
    }
}