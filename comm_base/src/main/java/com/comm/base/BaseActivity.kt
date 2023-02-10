package com.comm.base


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.comm.base.utils.LogUtils
import com.hjq.toast.ToastUtils
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity


/**
 *@author qiaohao
 *@date 21-4-2 上午9:59
 *@desc baseActivity Activity的基类
 */
abstract class BaseActivity<T : IViewModel> :
    RxAppCompatActivity(), LifecycleObserver,
    IBaseView {

    val TAG = javaClass.name

    var mViewModel: T? = null
    var mDataBinding: ViewDataBinding?=null

    /**
     * 资源文件id
     */
    abstract fun layoutResID(): Int

    /**
     * 初始化ViewModel
     */
    abstract fun iniViewModel(): Class<T>

    /**
     * 初始化组件
     */
    abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.fullScreen(this,true)
        mDataBinding = DataBindingUtil.setContentView(this,layoutResID())
//        mViewModel = iniViewModel()
        mViewModel = ViewModelProvider.AndroidViewModelFactory(this.application).create(
            iniViewModel())
//        getDataBinding().lifecycleOwner = this
        initViews()
    }



    //region lifecyler生命周期回调
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onCreate()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy(owner: LifecycleOwner?) {
        Log.i(this.javaClass.name, "onDestroy")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    open fun onAny(owner: LifecycleOwner?) {
//        Log.i(this.javaClass.name, "onAny")
    }
    //endregion
    override fun showLoading() {
        logE { "showLoading: " }
    }


    override fun hideLoading() {
        logE { "hideLoading: " }
    }

    override fun requestSuccess() {
//        toast("requestSuccess")
    }

    override fun requestFail(failMsg: String) {
//        toast("requestSuccess  $failMsg")
    }

    override fun requestComplete() {
//        toast("requestComplete")
    }

    override fun showMessage(msg: String) {
        ToastUtils.show(msg)
    }

     fun logE(content:()->String){
        LogUtils.instance.e(TAG) { content() }
    }

    fun logD(content:()->String){
        LogUtils.instance.d(TAG) { content() }
    }


    inline fun <reified T:Any> Activity.startToActivity(noinline init: Intent.()->Unit){
        val intent = Intent(this,T::class.java)
        intent.init()
        startActivity(intent)
    }


}