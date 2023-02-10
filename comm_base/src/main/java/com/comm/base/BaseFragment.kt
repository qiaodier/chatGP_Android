package com.comm.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.comm.base.IViewModel
import com.comm.base.IBaseView
import androidx.databinding.ViewDataBinding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.comm.base.utils.LogUtils
import com.hjq.toast.ToastUtils

/**
 * Created by iqiao on 2020-03-04 14:17
 * Desc:
 * @author iqiao
 */
abstract class BaseFragment<T : IViewModel?> : Fragment(), LifecycleObserver, IBaseView {

    val TAG = javaClass.name
    var mBinding: ViewDataBinding? = null
    @JvmField
    var viewModel: IViewModel? = null


    /**
     * 该方法必须重写
     *
     * @return 返回activity中对应的xml的id
     */
    protected abstract fun layoutResID(): Int
    protected abstract fun iniViewModel(): T
    protected abstract fun initViews(view: ViewDataBinding?)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, layoutResID(), container, false)
        mBinding?.setLifecycleOwner(activity)
        viewModel = iniViewModel()
        initViews(mBinding)
        return mBinding?.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onDestroy")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner?) {
//        Log.i(this.getClass().getName(), "onAny");
    }

    override fun showLoading() {

    }
    override fun hideLoading() {

    }
    override fun requestSuccess() {}
    override fun requestFail(failMsg: String) {}
    override fun requestComplete() {}
    override fun showMessage(message: String) {
        ToastUtils.show(message)
    }

    fun logE(content:()->String){
        LogUtils.instance.e(TAG) { content() }
    }

    fun logD(content:()->String){
        LogUtils.instance.d(TAG) { content() }
    }
}