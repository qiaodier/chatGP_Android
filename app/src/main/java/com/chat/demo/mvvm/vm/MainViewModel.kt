package com.chat.demo.mvvm.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chat.demo.mvvm.model.AiReq
import com.chat.demo.mvvm.repository.MainRepository
import com.comm.base.ui.BaseViewModel
import com.comm.http.model.NetResult
import com.hjq.toast.ToastUtils
import kotlinx.coroutines.launch

class MainViewModel:BaseViewModel() {

    private val mainRepository by lazy { MainRepository() }

    private var _aiResult = MutableLiveData<String>()

    val getAiResult:LiveData<String>
        get() = _aiResult


    fun getAiContent(req: AiReq) {
        viewModelScope.launch {
         val result = mainRepository.getAiContent(req)
            if (result is NetResult.Success){
                result.data?.let {
                    if (it.choices.isNullOrEmpty()) {
                        _aiResult.postValue("本次响应结果为空")
                        return@launch
                    }
                    _aiResult.postValue(it.choices!![0].text)
                }
            }else if(result is NetResult.Error){
                ToastUtils.show("本次响应错误,${result.exception.message}")
            }
        }
    }
}