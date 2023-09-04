package com.fullrandomstudio.core.ui.effect

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.LinkedList
import javax.inject.Inject

class EffectStateFlow @Inject constructor() {

    private val _queue = LinkedList<EffectState.DisplayEffect>()
    private val _state = MutableStateFlow<EffectState>(EffectState.Idle)
    val state: StateFlow<EffectState> = _state

    @Synchronized
    private fun emit(state: EffectState) {
        when (state) {
            is EffectState.DisplayEffect -> if (
                _queue.isEmpty() && _state.value == EffectState.Idle
            ) {
                _state.value = state
            } else {
                _queue.add(state)
            }

            is EffectState.Idle -> {
                throw IllegalArgumentException(
                    "State should be changed to ${EffectState.Idle} only in onDisplayed call"
                )
            }
        }
    }

    @Synchronized
    fun emit(command: Effect) {
        emit(EffectState.DisplayEffect(command))
    }

    @Synchronized
    fun onCollected(state: EffectState) {
        if (state == EffectState.Idle) {
            return
        }

        val currentState = _state.value
        check(
            currentState is EffectState.DisplayEffect &&
                    currentState.id == (state as EffectState.DisplayEffect).id
        ) { "An effect happened, that shouldn't be a current state" }

        if (_queue.isEmpty()) {
            _state.value = EffectState.Idle
        } else {
            emit(_queue.pop())
        }
    }
}
