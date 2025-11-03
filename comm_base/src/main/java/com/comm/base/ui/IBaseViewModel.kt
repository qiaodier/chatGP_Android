package com.comm.base.ui

import kotlinx.coroutines.flow.StateFlow

interface IBaseViewModel<out U : BaseUIState> {
    val uiState: StateFlow<U>
}
