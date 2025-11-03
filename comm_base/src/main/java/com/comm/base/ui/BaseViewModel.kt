package com.comm.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 *@author qiaohao
 *@date 21-4-2 上午10:25
 *
 */
abstract class BaseViewModel<U: BaseUIState>(
    private val createState: () -> U
) : ViewModel() {

    val TAG = this.javaClass.name

    protected val mUIState :U by lazy { createState() }
    protected val _UIState = MutableStateFlow(mUIState)
    val mainUIState: StateFlow<U>
        get() = _UIState.asStateFlow()

    fun <T : U> emitUIState(newState: T) {
        _UIState.value = newState
    }

    fun launchInViewModelScope(
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            block.invoke(this)
        }
    }

}