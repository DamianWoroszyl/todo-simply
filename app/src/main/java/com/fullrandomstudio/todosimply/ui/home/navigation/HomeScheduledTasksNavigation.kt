package com.fullrandomstudio.todosimply.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fullrandomstudio.task.ui.scheduled.ScheduledTasksPagerScreen

const val HOME_SCHEDULED_TASKS_ROUTE = "home/scheduledtasks"

fun NavController.navigateToHomeScheduledTasks(navOptions: NavOptions? = null) {
    this.navigate(HOME_SCHEDULED_TASKS_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScheduledTasks() {
    composable(
        route = HOME_SCHEDULED_TASKS_ROUTE,
    ) {
        ScheduledTasksPagerScreen()
    }
}
