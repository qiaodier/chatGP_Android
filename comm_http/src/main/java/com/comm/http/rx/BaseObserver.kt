package com.comm.http.rx


import com.comm.http.exception.NetworkError
import com.mvvm.kt.entity.BaseResp

import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 *@author qiaohao
 *@date 21-4-2 下午3:01
 */
abstract class BaseObserver<T:BaseResp> : Observer<T> {


    protected abstract fun onSuccess(t: T)

    protected abstract fun onFailure(errorMsg: String)




    init {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        if (t.isOk()) {
            onSuccess(t)
            return
        }
        onFailure(t.msg + "")
    }

    override fun onError(e: Throwable) {
        onNetError(e)
    }


    private fun onNetError(e: Throwable) {
        NetworkError.error(e)
    }
}