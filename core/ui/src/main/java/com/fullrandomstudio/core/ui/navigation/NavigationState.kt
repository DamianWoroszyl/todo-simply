package com.fullrandomstudio.core.ui.navigation

import java.util.UUID

interface NavigationCommand

sealed class NavigationState {
    object Idle : NavigationState()

    /**
     * @param id is used so that multiple instances of the same route will trigger multiple navigation calls.
     */
    data class Navigate(
        val command: NavigationCommand,
        val id: String = UUID.randomUUID().toString()
    ) : NavigationState()
}
