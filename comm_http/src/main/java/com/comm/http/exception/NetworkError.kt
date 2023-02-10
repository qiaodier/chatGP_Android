package com.comm.http.exception


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.comm.http.exception.RetrofitException.retrofitException
import com.hjq.toast.ToastUtils


/**
 * @author iqiao
 * @desc ：网络统一异常处理
 */
object NetworkError {


    // 全局监听网络状态toast提示
    var error = MutableLiveData<String>()
    /**
     * @param context 可以用于跳转Activity等操作
     */
    fun error(throwable: Throwable?) {
        Log.e("NetworkError", "error: ${throwable.toString()}" )
        val responeThrowable = retrofitException(throwable)
        when (responeThrowable.code) {
            RetrofitException.ERROR.UNKNOWN,
            RetrofitException.ERROR.PARSE_ERROR,
            RetrofitException.ERROR.NETWORD_ERROR,
            RetrofitException.ERROR.HTTP_ERROR,
            RetrofitException.ERROR.SSL_ERROR ->{
                error.postValue(responeThrowable.message)
//                Toast.makeText(
//                    context,
//                    responeThrowable.message,
//                    Toast.LENGTH_SHORT
//                ).show()
                ToastUtils.show("${responeThrowable.message}")
                Log.e("全局异常:NetworkError", "error: "+responeThrowable.message )
            }
            -1 -> {
            }
            else -> {
                error.postValue(responeThrowable.message)
                ToastUtils.show("${responeThrowable.message}")
                Log.e("全局异常:NetworkError", "error: "+responeThrowable.message )
//                Toast.makeText(context, responeThrowable.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}