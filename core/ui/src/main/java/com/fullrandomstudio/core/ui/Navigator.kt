package com.fullrandomstudio.core.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.LinkedList
import javax.inject.Inject

suspend fun MutableStateFlow<NavigationState>.emit(command: NavigationCommand) {
    emit(
        NavigationState.Navigate(command)
    )
}

class Navigator @Inject constructor() {

    private val _navigationQueue = LinkedList<NavigationState.Navigate>()
    private val _state = MutableStateFlow<NavigationState>(NavigationState.Idle)
    val state: StateFlow<NavigationState> = _state

    @Synchronized
    private fun navigate(navigationState: NavigationState) {
        when (navigationState) {
            is NavigationState.Navigate -> if (
                _navigationQueue.isEmpty() && _state.value == NavigationState.Idle
            ) {
                _state.value = navigationState
            } else {
                _navigationQueue.add(navigationState)
            }

            is NavigationState.Idle -> {
                throw IllegalArgumentException(
                    "State should be changed to ${NavigationState.Idle} only in onNavigated call"
                )
            }
        }
    }

    @Synchronized
    fun navigate(command: NavigationCommand) {
        navigate(NavigationState.Navigate(command))
    }

    @Synchronized
    fun onNavigated(navigatedState: NavigationState) {
        if (navigatedState == NavigationState.Idle) {
            return
        }

        val currentState = _state.value
        check(
            currentState is NavigationState.Navigate &&
                    currentState.id != (navigatedState as NavigationState.Navigate).id
        ) { "A navigation happened, that shouldn't be a current navigation state" }

        if (_navigationQueue.isEmpty()) {
            _state.value = NavigationState.Idle
        } else {
            navigate(_navigationQueue.pop())
        }
    }
}
