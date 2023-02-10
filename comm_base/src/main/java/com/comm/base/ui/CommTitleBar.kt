package com.comm.base.ui

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.comm.base.R
import com.comm.base.databinding.TitleBarCommLayoutBinding
import com.comm.base.utils.StatusBarUtils


class CommTitleBar(private val activity: Activity,onTitleBarHeightListener: OnTitleBarHeightListener) {

    private var titleBarCommLayoutBinding: TitleBarCommLayoutBinding = DataBindingUtil.inflate(
        LayoutInflater.from(activity), R.layout.title_bar_comm_layout, null, false
    )
    private val statusBarHeight = StatusBarUtils.getStatusBarHeight(activity)


    init {
        val layoutParams:LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.TOP
        activity.addContentView(titleBarCommLayoutBinding.root,layoutParams)
        titleBarCommLayoutBinding.statusBar.setPadding(0, statusBarHeight, 0, 0)
        titleBarCommLayoutBinding.llCommLayout.post{
            val titleBarHeight = titleBarCommLayoutBinding.llCommLayout.measuredHeight
            onTitleBarHeightListener.onBarHeight(titleBarHeight)
        }
    }

    /**
     * 设置是否显示titlebar
     */
    fun setShowTitleBar(flag: Boolean) {
        titleBarCommLayoutBinding.llCommLayout.visibility = if (flag) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }


    /**
     * 设置是否显示关闭 
     */
    fun setShowLeftText(flag: Boolean){
        titleBarCommLayoutBinding.tvCommLeftText.visibility = if (flag) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    /**
     * 设置右边图标/文字 点击回调
     */
    fun setOnRightIconClick(onClickListener: View.OnClickListener){
        titleBarCommLayoutBinding.ivRight.setOnClickListener(onClickListener)
        titleBarCommLayoutBinding.tvCommRightText.setOnClickListener(onClickListener)
    }

    /**
     * 返回图标点击
     */
    fun setOnBackClick(onClickListener: View.OnClickListener){
        titleBarCommLayoutBinding.ivBack1.setOnClickListener(onClickListener)
    }


    /**
     * 设置左边图标
     */
    fun setLeftIcon(resource: Int) {
        if (resource==0){
            titleBarCommLayoutBinding.ivBack.visibility = View.GONE
        }else{
            titleBarCommLayoutBinding.ivBack.visibility = View.VISIBLE
            titleBarCommLayoutBinding.ivBack.setImageResource(resource)
        }
    }


    /**
     * 设置标题内容
     */
    fun setCommTitleText(content:String){
        titleBarCommLayoutBinding.tvCommTitle.text = content
    }

    /**
     * 设置公共标题栏背景颜色
     */
    fun setCommTitleBgColor(colorId:Int){
//        titleBarCommLayoutBinding.llCommLayout.setBackgroundColor(colorId)
        titleBarCommLayoutBinding.statusBar.setBackgroundColor(activity.resources.getColor(colorId))
        titleBarCommLayoutBinding.rlTitle.setBackgroundColor(activity.resources.getColor(colorId))
    }
    /**
     * 设置右边图标
     */
    fun setRightIcon(resource: Int) {
        if (resource==0){
            titleBarCommLayoutBinding.ivRight.visibility = View.GONE
        }else{
            titleBarCommLayoutBinding.ivRight.visibility = View.VISIBLE
            titleBarCommLayoutBinding.ivRight.setImageResource(resource)
        }
    }

    /**
     * 设置title 右边文字
     */
    fun setRightText(resource: String){
        titleBarCommLayoutBinding.tvCommRightText.text = resource
        titleBarCommLayoutBinding.tvCommRightText.visibility = View.VISIBLE
    }


    interface OnTitleBarHeightListener{
        fun onBarHeight(height:Int)
    }


}