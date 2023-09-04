package com.fullrandomstudio.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun CollectNavigationAsFlow(
    navigationStateFlow: NavigationStateFlow,
    autoCollect: Boolean = true,
    onNavigationStateChange: (NavigationState, NavigationCommand?) -> Unit
) {
    val onNavigationStateChangeUpdated by rememberUpdatedState(newValue = onNavigationStateChange)

    LaunchedEffect(navigationStateFlow) {
        navigationStateFlow.state.collect { navState ->
            when (navState) {
                is NavigationState.Idle -> {} // nothing
                is NavigationState.Navigate -> onNavigationStateChangeUpdated(
                    navState, (navState as? NavigationState.Navigate)?.command
                )
            }

            if (autoCollect) {
                navigationStateFlow.onNavigated(navState)
            }
        }
    }
}
