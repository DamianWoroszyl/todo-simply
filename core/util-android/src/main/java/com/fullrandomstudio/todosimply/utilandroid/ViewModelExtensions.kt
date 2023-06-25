package com.fullrandomstudio.todosimply.utilandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

fun <T> Flow<T>.stateInViewModel(viewModel: ViewModel, initValue: T): StateFlow<T> {
    return stateIn(viewModel.viewModelScope, WhileSubscribed(5_000), initValue)
}
