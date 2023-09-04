package com.fullrandomstudio.core.ui.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle

// todo maybe in future we could use this as delegate, same as 'by collectAsStateWithLifecycle'
@Composable
fun CollectEffectAsState(
    autoCollect: Boolean = true,
    effectStateFlow: EffectStateFlow,
    onEffectStateChange: @Composable (EffectState, Effect?) -> Unit
) {
    val state by effectStateFlow.state.collectAsStateWithLifecycle()

    when (state) {
        is EffectState.Idle -> {} // nothing
        is EffectState.DisplayEffect -> onEffectStateChange(
            state, (state as? EffectState.DisplayEffect)?.value
        )
    }

    if (autoCollect) {
        effectStateFlow.onCollected(state)
    }
}

@Composable
fun CollectEffectAsFlow(
    autoCollect: Boolean = true,
    effectStateFlow: EffectStateFlow,
    onEffectStateChange: (EffectState, Effect?) -> Unit
) {
    val onEffectStateChangeUpdated by rememberUpdatedState(newValue = onEffectStateChange)

    LaunchedEffect(effectStateFlow) {
        effectStateFlow.state.collect { state ->
            when (state) {
                is EffectState.Idle -> {} // nothing
                is EffectState.DisplayEffect -> onEffectStateChangeUpdated(
                    state, (state as? EffectState.DisplayEffect)?.value
                )
            }

            if (autoCollect) {
                effectStateFlow.onCollected(state)
            }
        }
    }
}
