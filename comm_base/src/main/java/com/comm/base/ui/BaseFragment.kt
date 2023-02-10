package com.comm.base.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.comm.base.extensions.getViewBinding
import com.comm.base.extensions.getViewModel
import com.hjq.toast.ToastUtils


abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding> : Fragment() {
    val TAG = this.javaClass.name
    protected lateinit var mViewModel: VM
    protected lateinit var mViewBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        mViewBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        mViewBinding = getViewBinding(inflater, container)
        return mViewBinding.root
    }




    abstract fun initData()

    abstract fun initView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = getViewModel(requireActivity())
        initView()
        initData()
    }

    /**
     * 新增toast高阶封装
     */
    fun showMessage(msg:()->String){
        ToastUtils.show(msg())
    }
    /**
     * 新增loge高阶封装
     */
    fun logE(msg: () -> String){
        Log.e(TAG,msg())
    }

    inline fun <reified T:Any> Fragment.startToActivity(noinline init: Intent.()->Unit){
        val intent = Intent(requireContext(),T::class.java)
        intent.init()
        startActivity(intent)
    }
}