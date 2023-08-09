@file:Suppress("ConstPropertyName", "TopLevelPropertyNaming")

package com.fullrandomstudio.task.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fullrandomstudio.todosimply.task.domain.TaskEditType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val ArgTaskId = "taskId"
private const val ArgScheduled = "scheduled"
private const val ArgTaskEditType = "taskEditType"
private const val ArgSelectedDate = "selectedDate"

private val selectedDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

internal class TaskEditArgs(
    val taskId: Long?,
    val scheduled: Boolean,
    val taskEditType: TaskEditType,
    val selectedDate: LocalDate?
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        taskId = savedStateHandle[ArgTaskId],
        scheduled = requireNotNull(savedStateHandle[ArgScheduled]),
        taskEditType = requireNotNull(savedStateHandle[ArgTaskEditType]),
        selectedDate = LocalDate.parse(
            savedStateHandle[ArgSelectedDate],
            selectedDateFormatter
        ),
    )
}

// todo dw find patterns to creating routes
const val TASK_EDIT_NAV_ROUTE = "task/edit?taskId={$ArgTaskId}&" +
        "scheduled={$ArgScheduled}&" +
        "taskEditType={$ArgTaskEditType}&" +
        "selectedDate={$ArgSelectedDate}"

fun NavController.navigateToTaskEdit(
    taskId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        "task/edit?taskId=$taskId&taskEditType=${TaskEditType.EDIT.name}",
        navOptions
    )
}

fun NavController.navigateToCreateTask(
    scheduled: Boolean,
    selectedDate: LocalDate? = null,
    navOptions: NavOptions? = null
) {
    this.navigate(
        "task/edit?scheduled=$scheduled&" +
                "taskEditType=${TaskEditType.CREATE.name}&" +
                "selectedDate=${selectedDateFormatter.format(selectedDate)}",
        navOptions
    )
}

fun NavController.navigateToTaskEdit(
    navOptions: NavOptions? = null
) {
    this.navigate(TASK_EDIT_NAV_ROUTE, navOptions)
}

fun NavGraphBuilder.taskEdit() {
    composable(
        route = TASK_EDIT_NAV_ROUTE,
        arguments = listOf(
            navArgument(ArgTaskId) { nullable = true },
            navArgument(ArgScheduled) { defaultValue = true },
            navArgument(ArgTaskEditType) { defaultValue = TaskEditType.EDIT },
            navArgument(ArgSelectedDate) { nullable = true }
        )
    ) {
        TaskEditScreen()
    }
}
