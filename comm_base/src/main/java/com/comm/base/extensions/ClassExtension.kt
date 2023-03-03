package com.comm.base.extensions

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

import androidx.viewbinding.ViewBinding

import com.comm.base.ui.BaseViewModel

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy
import java.text.DecimalFormat
import kotlin.reflect.KClass


//activity 获取viewbinding
inline fun <VB : ViewBinding> Any.getViewBinding(inflater: LayoutInflater, position: Int = 1): VB {
    val vbClass =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
    val inflate = vbClass[position].getDeclaredMethod("inflate", LayoutInflater::class.java)
    return inflate.invoke(null, inflater) as VB
}

//activity/fragment 获取viewmodel 该方法是viewmodel 构造器无参的情况下
inline fun <VM : BaseViewModel> Any.getViewModel(activity: Activity, position: Int = 0): VM {
    val vbClass =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VM>>()
    return ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)
        .create(vbClass[position])
}

// fragment 获取viewbinding
inline fun <VB : ViewBinding> Any.getViewBinding(
    inflater: LayoutInflater,
    container: ViewGroup?,
    position: Int = 1
): VB {
    val vbClass =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
    val inflate = vbClass[position].getDeclaredMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )
    return inflate.invoke(null, inflater, container, false) as VB
}

//adapter 中反射获取 ViewDataBinding
inline fun <VB : ViewDataBinding> Any.getViewDataBinding(
    inflater: LayoutInflater,
    position: Int = 1
): VB {
    val vbClass =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
    val inflate = vbClass[position].getDeclaredMethod("inflate", LayoutInflater::class.java)
    return inflate.invoke(null, inflater) as VB
}

//
//inline fun <VM: ViewModel> ComponentActivity.createViewModel(position:Int): VM {
//    val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
//    val viewModel = vbClass[position] as Class<VM>
//    return ViewModelProvider(this).get(viewModel)
//}

/**
 * double 格式化
 */
fun Double.toFormatString(): String {
    val de = DecimalFormat("####0.00")
    return de.format(this)
}

/**
 * 启动activity
 */
inline fun <reified T : Any> Activity.startToActivity(noinline init: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}

/**
 * 点击事件
 */
fun View.onClick(onClick: (view: View) -> Unit) {
    setOnClickListener(onClick)
}

/**
 * 是否显示
 */
fun View.onShow(bool: () -> Boolean) {
    this.visibility = if (bool()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * 手机号码 中间4位隐藏
 */
fun String.phoneProcess(): String {
    if (this.isNullOrBlank()) {
        return ""
    }
    if (this.length != 11) {
        return this
    }
    return this.replace("(\\d{3})\\d{4}(\\d{4})".toRegex(), "$1****$2")
}

inline fun <reified T :Any> noOverrideDelegate():T{
    val javaClass = T::class.java
    return Proxy.newProxyInstance(javaClass.classLoader, arrayOf(javaClass)){ _, _, _ ->} as T
}