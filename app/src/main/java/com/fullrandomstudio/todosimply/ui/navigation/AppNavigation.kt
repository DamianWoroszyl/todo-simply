@file:Suppress("MatchingDeclarationName")

package com.fullrandomstudio.todosimply.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fullrandomstudio.task.ui.edit.TaskEditArgs
import com.fullrandomstudio.task.ui.edit.TaskEditArgs.Companion.argSelectedDate
import com.fullrandomstudio.task.ui.edit.TaskEditArgs.Companion.argTaskId
import com.fullrandomstudio.task.ui.edit.TaskEditArgs.Companion.selectedDateFormatter
import com.fullrandomstudio.task.ui.edit.TaskEditScreen
import com.fullrandomstudio.task.ui.edit.TaskType
import com.fullrandomstudio.todosimply.task.domain.TaskEditType
import com.fullrandomstudio.todosimply.ui.home.HomeScreen

internal sealed class Screen {

    abstract val route: String

    object Home : Screen() {
        override val route: String = "home"
    }

    object TaskEdit : Screen() {
        private const val routeTaskEditBase = "task/edit"
        private const val routeTaskEdit =
            "$routeTaskEditBase/" +
                    "{${TaskEditArgs.argTaskType}}/" +
                    "{${TaskEditArgs.argTaskEditType}}?" +
                    "${TaskEditArgs.argTaskId}={${TaskEditArgs.argTaskId}}&" +
                    "${TaskEditArgs.argSelectedDate}={${TaskEditArgs.argSelectedDate}}"

        override val route: String = routeTaskEdit

        fun createRoute(
            args: TaskEditArgs
        ): String {
            val type = if (args.scheduled) TaskType.SCHEDULED else TaskType.GENERAL
            val selectedDateArg = args.selectedDate?.let {
                "&$argSelectedDate=${selectedDateFormatter.format(args.selectedDate)}"
            } ?: ""

            return "$routeTaskEditBase/" +
                    "${type.name}/" +
                    "${args.taskEditType.name}?" +
                    "$argTaskId=${args.taskId}" +
                    selectedDateArg
        }
    }
}

@Composable
internal fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        addHome(navController)
        addTaskEdit(navController)
    }
}

private fun NavGraphBuilder.addHome(
    navController: NavController
) {
    composable(
        route = Screen.Home.route
    ) {
        HomeScreen(
            onEditTask = {
                navController.navigate(
                    Screen.TaskEdit.createRoute(it)
                )
            }
        )
    }
}

fun NavGraphBuilder.addTaskEdit(
    navController: NavController
) {
    composable(
        route = Screen.TaskEdit.route,
        arguments = listOf(
            navArgument(TaskEditArgs.argTaskType) {
                type = NavType.EnumType(TaskType::class.java)
            },
            navArgument(TaskEditArgs.argTaskEditType) {
                type = NavType.EnumType(TaskEditType::class.java)
            },
            navArgument(TaskEditArgs.argTaskId) {
                type = NavType.LongType
            },
        )
    ) {
        TaskEditScreen(
            onPop = { navController.popBackStack() }
        )
    }
}
