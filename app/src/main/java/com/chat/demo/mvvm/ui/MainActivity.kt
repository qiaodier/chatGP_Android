package com.chat.demo.mvvm.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chat.demo.adapter.ChatAdapter
import com.chat.demo.databinding.ActivityMainBinding
import com.chat.demo.mvvm.model.AiReq
import com.chat.demo.mvvm.model.ChatModel
import com.chat.demo.mvvm.vm.MainUIState
import com.chat.demo.mvvm.vm.MainViewModel
import com.comm.base.ui.BaseActivity
import com.comm.base.ui.BaseUIState
import com.comm.base.ui.BaseViewModel
import com.hjq.toast.ToastUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
                if (mChatAdapter?.itemCount == 0) {
                    mChatAdapter?.setData(mChatDataList)
                } else {
                    mChatAdapter?.addData(mChatDataList)
                }
                mChatDataList.clear()
                mViewModel.getAiContent(AiReq(inputContent.toString()))
            }
        }
    }

    override fun initData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.mainUIState.collect {
                    it.result?.let {
                        mChatDataList.add(ChatModel(1, it))
                        mChatAdapter?.addData(mChatDataList)
                        mChatDataList.clear()
                        mDataBinding.recyclerView.scrollToPosition(
                            mChatAdapter?.itemCount ?: (0 - 1)
                        )
                    }
                    it.errorMsg?.let {
                        showMessage("$it")
                    }
                }
            }
        }
    }
}