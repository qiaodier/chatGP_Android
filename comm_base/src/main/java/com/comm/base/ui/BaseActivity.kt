package com.comm.base.ui


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.comm.base.extensions.getViewBinding
import com.comm.base.extensions.getViewModel
import com.comm.base.utils.LogUtils
import com.comm.base.utils.StatusBarColorUtil
import com.hjq.toast.ToastUtils
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 *@author qiaohao
 *@date 21-4-2 上午9:59
 *@desc baseActivity Activity的基类
 */
abstract class BaseActivity<VM : BaseViewModel<*>, VB : ViewDataBinding> : RxAppCompatActivity() {

    val TAG = this.javaClass.name
//    private lateinit var loadingDialog: LoadingDialog

    protected val mViewModel: VM by lazy(mode = LazyThreadSafetyMode.NONE) {
        getViewModel(this)
    }
    protected val mDataBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        getViewBinding(layoutInflater)
    }
    private lateinit var mCommTitleBar: CommTitleBar
    var mTitleBarHeight: Int = 0


//    /**
//     * 资源文件id
//     */
//    abstract fun layoutResID(): Int

//    /**
//     * 初始化ViewModel
//     */
//    abstract fun iniViewModelClass(): Class<VM>

    /**
     * 初始化组件
     */
    abstract fun initViews()

    /**
     * 初始化页面数据
     */
    abstract fun initData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarColorUtil.fullScreen(this, true)
//        mDataBinding = DataBindingUtil.setContentView(this, layoutResID())
        setContentView(mDataBinding.root)
//        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//            .create(iniViewModelClass())
//        lifecycle.addObserver(mViewModel)
        initTitleBar()
        setCommTitleRightIcon(0)
        setShowLeftText(false)
        initViews()
        initData()
    }

    /**
     * 初始化titlebar
     */
    private fun initTitleBar() {
        mCommTitleBar = CommTitleBar(this, object : CommTitleBar.OnTitleBarHeightListener {
            override fun onBarHeight(height: Int) {
                mTitleBarHeight = height
                Log.e(TAG, "onBarHeight: $mTitleBarHeight")
                setRootViewTopMargin(mTitleBarHeight)
            }
        })
        mCommTitleBar.setOnBackClick { onBackClick() }
        mCommTitleBar.setOnRightIconClick { onRightClick() }
    }

    /**
     * 子类如果有特殊需求，不需要margin 的话，需要重写此方法，
     */
    open fun setRootViewTopMargin(height: Int) {
        val layoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.topMargin = height
        Log.e("BaseActivity", "initTitleBar: $height")
        mDataBinding.root.layoutParams = layoutParams
    }

    /**
     * 设置是否显示titlebar
     */
    fun setShowCommTitleBar(flag: Boolean) {
        mCommTitleBar.setShowTitleBar(flag = flag)
        if (flag) initTitleBar()
    }

    /**
     * 设置是否显示左边 “关闭”
     */
    fun setShowLeftText(flag: Boolean) {
        mCommTitleBar.setShowLeftText(flag = flag)
    }

    /**
     * 设置titlbar 左边图标
     */
    fun setCommTitleLeftIcon(resource: Int) {
        mCommTitleBar.setLeftIcon(resource)
    }

    /**
     * 设置titlebar 右边图标
     */
    fun setCommTitleRightIcon(resource: Int) {
        mCommTitleBar.setRightIcon(resource)
    }

    /**
     * 设置titile 右边文字
     */
    fun setRightText(resource: String){
        mCommTitleBar.setRightText(resource)
    }

    /**
     * 设置titlebar 标题内容
     */
    fun setCommTitleText(content: String) {
        mCommTitleBar.setCommTitleText(content = content)
    }

    /**
     * 设置titlebar背景颜色
     */
    fun setCommTitleBgColor(colorId: Int) {
        mCommTitleBar.setCommTitleBgColor(colorId)
    }

    /**
     * titlbar 右边图标点击
     */
    open fun onRightClick() {

    }

    /**
     * titlebar 左边图标点击
     */
    open fun onBackClick() {
        onBackPressed()
    }

    fun showMessage(msg: String) {
        ToastUtils.show(msg)
    }

    fun logE(content:()->String){
        LogUtils.instance.e(TAG) { content() }
    }

    fun logD(content:()->String){
        LogUtils.instance.d(TAG) { content() }
    }

    fun showLoading() {
//        if (!loadingDialog.isShowing) loadingDialog.show()
    }


    fun hideLoading() {
//        if (loadingDialog.isShowing) loadingDialog.dismiss()
    }

//    /**
//     * 复用 网络请求加载loading
//     */
//    inline fun <T:Any> Any.flowRequest(flow:Flow<T>): Flow<T> {
//        return flow.onStart {
//            showLoading()
//        }.onCompletion {
//            hideLoading()
//        }
//    }
    /**
     * 启动activity
     */
    inline fun <reified T:Any> Activity.startToActivity(noinline init: Intent.()->Unit){
        val intent = Intent(this,T::class.java)
        intent.init()
        startActivity(intent)
    }

    /**
     * 点击事件
     */
    fun View.onClick(onClick:(view:View) ->Unit){
        this.setOnClickListener(onClick)
    }

    /**
     * 是否显示
     */
    fun View.onShow(bool:() ->Boolean){
        this.visibility = if (bool()){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    fun repeatOnLifecycleCreated(
        block: suspend CoroutineScope.() -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                block.invoke(this)
            }
        }
    }

}