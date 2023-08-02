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

private const val argTaskId = "taskId"
private const val argScheduled = "scheduled"
private const val argTaskEditType = "taskEditType"
private const val argSelectedDate = "selectedDate"

private val selectedDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

internal class TaskEditArgs(
    val taskId: Long?,
    val scheduled: Boolean,
    val taskEditType: TaskEditType,
    val selectedDate: LocalDate?
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        taskId = savedStateHandle[argTaskId],
        scheduled = requireNotNull(savedStateHandle[argScheduled]),
        taskEditType = requireNotNull(savedStateHandle[argTaskEditType]),
        selectedDate = LocalDate.parse(
            savedStateHandle[argSelectedDate], selectedDateFormatter
        ),
    )
}

// todo dw find patterns to creating routes
const val TASK_EDIT_NAV_ROUTE =
    "task/edit?taskId={$argTaskId}&scheduled={$argScheduled}&taskEditType={$argTaskEditType}&selectedDate={$argSelectedDate}"

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
            navArgument(argTaskId) { nullable = true },
            navArgument(argScheduled) { defaultValue = true },
            navArgument(argTaskEditType) { defaultValue = TaskEditType.EDIT },
            navArgument(argSelectedDate) { nullable = true }
        )
    ) {
        TaskEditScreen()
    }
}
