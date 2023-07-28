package com.fullrandomstudio.task.ui.edit

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

// todo dw later add nav args
const val TASK_EDIT_NAV_ROUTE = "task/edit"

fun NavController.navigateToTaskEdit(navOptions: NavOptions? = null) {
    this.navigate(TASK_EDIT_NAV_ROUTE, navOptions)
}

fun NavGraphBuilder.taskEdit() {
    composable(
        route = TASK_EDIT_NAV_ROUTE
    ) {
        TaskEditScreen()
    }
}
