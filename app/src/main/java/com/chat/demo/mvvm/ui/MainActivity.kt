package com.chat.demo.mvvm.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.chat.demo.adapter.ChatAdapter
import com.chat.demo.databinding.ActivityMainBinding
import com.chat.demo.mvvm.model.AiReq
import com.chat.demo.mvvm.model.ChatModel
import com.chat.demo.mvvm.vm.MainViewModel
import com.comm.base.ui.BaseActivity
import com.hjq.toast.ToastUtils

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private var mChatAdapter: ChatAdapter? = null
    private var mChatDataList = mutableListOf<ChatModel>()
    override fun initViews() {
        setCommTitleLeftIcon(0)
        setCommTitleText("ChatGPT")
        mDataBinding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            mChatAdapter = ChatAdapter()
            recyclerView.adapter = mChatAdapter
            btnSend.onClick {
                val inputContent = etContent.text?.toString()
                if (inputContent.isNullOrBlank()) {
                    ToastUtils.show("请输入内容进行发送")
                    return@onClick
                }
                etContent.setText("")
                mChatDataList.add(ChatModel(2, inputContent.toString()))
                if (mChatAdapter?.itemCount ==0){
                    mChatAdapter?.setData(mChatDataList)
                }else{
                    mChatAdapter?.addData(mChatDataList)
                }
                mChatDataList.clear()
                mViewModel.getAiContent(AiReq(inputContent.toString()))
            }
            mViewModel.getAiResult.observe(this@MainActivity) {
                mChatDataList.add(ChatModel(1, it))
                mChatAdapter?.addData(mChatDataList)
                mChatDataList.clear()
                recyclerView.scrollToPosition(mChatAdapter?.itemCount ?: (0 - 1))
            }
        }
    }

    override fun initData() {


    }

}