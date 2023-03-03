package com.chat.demo.mvvm.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chat.demo.mvvm.model.AiReq
import com.chat.demo.mvvm.repository.MainRepository
import com.comm.base.ui.BaseViewModel
import com.comm.http.model.NetResult
import com.hjq.toast.ToastUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MainUIState(
    val result: String? = null,
    val errorMsg: String? = null
)

class MainViewModel : BaseViewModel() {

    private val mainRepository by lazy { MainRepository() }
    private val mMainUIState by lazy { MainUIState() }

    private val _aiResult = MutableStateFlow(mMainUIState)
    val getAiResult: StateFlow<MainUIState>
        get() = _aiResult.asStateFlow()


    fun getAiContent(req: AiReq) {
        viewModelScope.launch {
            mainRepository.getAiContent(req).onSuccess {
                it?.let {
                    _aiResult.value = mMainUIState.copy(result = it.choices!![0].text)
                } ?: run {
                    _aiResult.value = mMainUIState.copy(errorMsg = "响应内容为空")
                }
            }.onError {
                _aiResult.value = mMainUIState.copy(errorMsg = "本次响应错误,${it.message}")
            }
        }
    }
}