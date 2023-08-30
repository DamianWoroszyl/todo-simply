@file:Suppress("MatchingDeclarationName")

package com.fullrandomstudio.todosimply.ui.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fullrandomstudio.task.ui.edit.TaskEditArgs
import com.fullrandomstudio.task.ui.scheduled.ScheduledTasksPagerScreen

sealed class HomeTabScreen {
    abstract val route: String

    object ScheduledTasks : HomeTabScreen() {
        override val route: String = "home/scheduledtasks"
    }
}

@Composable
internal fun HomeNavigation(
    navController: NavHostController,
    onEditTask: (taskEditArgs: TaskEditArgs) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeTabScreen.ScheduledTasks.route,
        modifier = modifier
    ) {
        homeScheduledTasks(onEditTask)
    }
}

private fun NavGraphBuilder.homeScheduledTasks(
    onEditTask: (taskEditArgs: TaskEditArgs) -> Unit,
) {
    composable(
        route = HomeTabScreen.ScheduledTasks.route,
    ) {
        ScheduledTasksPagerScreen(
            onEditTask = onEditTask,
        )
    }
}
