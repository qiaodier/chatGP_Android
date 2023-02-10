package com.comm.http.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * @author iqiao
 */
object RetrofitException {
    /**
     * http的错误状态码
     */
    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504
    @JvmStatic
    fun retrofitException(e: Throwable?): ResponeThrowable {
        val ex: ResponeThrowable
        return if (e is HttpException) {
            ex = ResponeThrowable(e, ERROR.HTTP_ERROR)
            when (e.code()) {
                UNAUTHORIZED -> ex.message = "请求未被授权"
                FORBIDDEN -> ex.message = "请求被禁止"
                NOT_FOUND -> ex.message = "请求地址未找到"
                REQUEST_TIMEOUT -> ex.message = "请求超时"
                GATEWAY_TIMEOUT -> ex.message = "网关超时"
                INTERNAL_SERVER_ERROR -> ex.message = "内部服务器错误"
                BAD_GATEWAY -> ex.message = "网关错误"
                SERVICE_UNAVAILABLE -> ex.message = "暂停服务"
                else -> ex.message = "网络错误"
            }
            ex
        } else if (e is ServerException) {
            // 服务器下发的错误
            val resultException =
                e
            ex = ResponeThrowable(resultException, resultException.code)
            ex.message = resultException.message
            ex
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {
            ex = ResponeThrowable(e, ERROR.PARSE_ERROR)
            ex.message = "解析错误，请检查字段类型是否匹配"
            ex
        } else if (e is ConnectException
            || e is UnknownHostException
        ) {
            ex = ResponeThrowable(e, ERROR.NETWORD_ERROR)
            ex.message = "连接失败"
            ex
        } else if(e is SocketTimeoutException){
            ex = ResponeThrowable(e, ERROR.NETWORD_ERROR)
            ex.message = "连接超时"
            ex
        }else if (e is SSLHandshakeException) {
            ex = ResponeThrowable(e, ERROR.SSL_ERROR)
            ex.message = "证书验证失败"
            ex
        } else if (e is MalformedJsonException) {
            ex = ResponeThrowable(e, ERROR.UNKNOWN_JSON)
            ex.message = "json格式错误"
            ex
        } else {
            ex = ResponeThrowable(e, ERROR.UNKNOWN)
            ex.message = "未知错误"
            ex
        }
    }

    /**
     * 与服务器同步的异常状态码
     */
    internal object ERROR {
        /**
         * json格式错误
         */
        const val UNKNOWN_JSON = 999

        /**
         * 未知错误
         */
        const val UNKNOWN = 1000

        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1001

        /**
         * 网络错误
         */
        const val NETWORD_ERROR = 1002

        /**
         * 协议出错
         */
        const val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        const val SSL_ERROR = 1005
    }

    class ResponeThrowable(throwable: Throwable?, var code: Int) :
        Exception(throwable) {

        override var message: String? = throwable?.message

    }
}