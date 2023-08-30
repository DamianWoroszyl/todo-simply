package com.fullrandomstudio.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun CollectNavigation(
    navigator: Navigator,
    autoCollect: Boolean = true,
    onNavigationStateChange: (NavigationState, NavigationCommand?) -> Unit
) {
    LaunchedEffect(navigator) {
        navigator.state.collect { navState ->
            when (navState) {
                is NavigationState.Idle -> {} // nothing
                is NavigationState.Navigate -> onNavigationStateChange(
                    navState, (navState as? NavigationState.Navigate)?.command
                )
            }

            if (autoCollect) {
                navigator.onNavigated(navState)
            }
        }
    }
}
