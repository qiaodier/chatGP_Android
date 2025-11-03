package com.chat.demo.mvvm.vm

import com.chat.demo.mvvm.model.AiReq
import com.chat.demo.mvvm.repository.MainRepository
import com.comm.base.ui.BaseUIState
import com.comm.base.ui.BaseViewModel

data class MainUIState(
    val result: String? = null,
    val errorMsg: String? = null
) : BaseUIState()

class MainViewModel : BaseViewModel<MainUIState>({ MainUIState() }) {

    private val mainRepository by lazy { MainRepository() }
    fun getAiContent(req: AiReq) = launchInViewModelScope {
        mainRepository
            .getAiContent(req)
            .onSuccess {
                it?.let {
                    emitUIState(mUIState.copy(result = it.choices!![0].text))
                } ?: run {
                    emitUIState(mUIState.copy(errorMsg = "响应内容为空"))
                }
            }.onError {
                emitUIState(mUIState.copy(errorMsg = "本次响应错误,${it.message}"))
            }
    }

}