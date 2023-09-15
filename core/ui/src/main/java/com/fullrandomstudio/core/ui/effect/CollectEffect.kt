package com.fullrandomstudio.core.ui.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
inline fun <reified T : Effect> CollectEffectAsFlow(
    autoCollect: Boolean = true,
    effectStateFlow: EffectStateFlow<T>,
    noinline onEffectStateChange: (EffectState, T) -> Unit
) {
    val onEffectStateChangeUpdated by rememberUpdatedState(newValue = onEffectStateChange)

    LaunchedEffect(effectStateFlow) {
        effectStateFlow.state.collect { state ->
            when (state) {
                is EffectState.Idle -> {} // nothing
                is EffectState.DisplayEffect -> onEffectStateChangeUpdated(
                    state, state.value as T
                )
            }

            if (autoCollect) {
                effectStateFlow.onCollected(state)
            }
        }
    }
}
