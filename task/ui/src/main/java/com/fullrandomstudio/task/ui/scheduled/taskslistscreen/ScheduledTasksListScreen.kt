@file:OptIn(ExperimentalFoundationApi::class)

package com.fullrandomstudio.task.ui.scheduled.taskslistscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fullrandomstudio.core.ui.effect.CollectEffectAsFlow
import com.fullrandomstudio.core.ui.navigation.CollectNavigationAsFlow
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.task.ui.common.EditTask
import com.fullrandomstudio.task.ui.edit.TaskEditArgs
import com.fullrandomstudio.task.ui.list.item.TaskAction
import com.fullrandomstudio.task.ui.list.item.TaskCategoryMarkerListItem
import com.fullrandomstudio.task.ui.list.item.TaskCategoryMarkerListItemUiState
import com.fullrandomstudio.task.ui.list.item.TaskListItem
import com.fullrandomstudio.task.ui.list.item.TaskListItemUiState
import com.fullrandomstudio.task.ui.list.item.TasksDateMarkerListItem
import com.fullrandomstudio.task.ui.list.item.TasksDateMarkerListItemUiState
import com.fullrandomstudio.task.ui.list.item.TasksListItem
import com.fullrandomstudio.task.ui.previewdata.previewDoneTaskUiState
import com.fullrandomstudio.task.ui.previewdata.previewTodoTaskUiState
import com.fullrandomstudio.task.ui.scheduled.taskslistscreen.effect.DeleteTaskEffect

@Composable
fun ScheduledTasksListScreen(
    onEditTask: (taskEditArgs: TaskEditArgs) -> Unit,
    onShowDeleteSnackbar: (taskId: Long, taskName: String) -> Unit,
    viewModel: ScheduledTasksListViewModel,
    modifier: Modifier = Modifier,
) {
    val items by viewModel.items.collectAsStateWithLifecycle()

    ScheduledTasksListScreen(
        items = items,
        onTaskClick = { taskId -> viewModel.onTaskClick(taskId) },
        onTaskDoneClick = { taskId, isFinished -> viewModel.onTaskDoneClick(taskId, isFinished) },
        onToggleAlarm = { taskId -> viewModel.onToggleAlarm(taskId) },
        onTaskActionClick = { taskId, taskAction ->
            viewModel.onTaskActionClick(taskId, taskAction)
        },
        modifier = modifier
    )

    CollectNavigationAsFlow(
        navigationStateFlow = viewModel.navigationStateFlow,
    ) { _, command ->
        when (command) {
            is EditTask -> onEditTask(command.args)
        }
    }

    CollectEffectAsFlow(
        effectStateFlow = viewModel.effectStateFlow,
    ) { _, effect ->
        when (effect) {
            is DeleteTaskEffect -> onShowDeleteSnackbar(effect.taskId, effect.taskName)
        }
    }
}

@Composable
fun ScheduledTasksListScreen(
    items: List<TasksListItem>,
    onTaskDoneClick: (taskId: Long, isFinished: Boolean) -> Unit,
    onTaskClick: (taskId: Long) -> Unit,
    onToggleAlarm: (taskId: Long) -> Unit,
    onTaskActionClick: (taskId: Long, taskAction: TaskAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier
                .fillMaxSize()
        ) {
            items(
                items = items,
                key = { it.key() },
                contentType = { it.contentType() }
            ) {
                when (it) {
                    is TaskCategoryMarkerListItemUiState -> TaskCategoryMarkerListItem(it)
                    is TasksDateMarkerListItemUiState -> TasksDateMarkerListItem(it)
                    is TaskListItemUiState -> TaskListItem(
                        state = it,
                        onClick = onTaskClick,
                        onTaskDoneClick = onTaskDoneClick,
                        onToggleAlarm = onToggleAlarm,
                        onTaskActionClick = onTaskActionClick,
                        modifier = Modifier.animateItemPlacement()
                        // todo check performance on release build on real device,
                        // with and without animateItemPlacement()
                    )
                }
            }
        }
    }
}

private fun TasksListItem.contentType(): String {
    return when (this) {
        is TaskCategoryMarkerListItemUiState -> "task-category"
        is TaskListItemUiState -> "task"
        is TasksDateMarkerListItemUiState -> "date-marker"
    }
}

private fun TasksListItem.key(): String {
    return when (this) {
        is TaskCategoryMarkerListItemUiState -> "taskcategory-${this.taskCategory.id}"
        is TaskListItemUiState -> "task-${this.task.id}"
        is TasksDateMarkerListItemUiState -> "date-${this.date.toLocalDate()}"
    }
}

@Preview
@Composable
internal fun TasksListScreenPreview() {
    TodoSimplyTheme {
        ScheduledTasksListScreen(
            items = listOf(
                previewTodoTaskUiState(),
                previewDoneTaskUiState()
            ),
            onTaskDoneClick = { _, _ -> },
            onTaskClick = { _ -> },
            onToggleAlarm = { _ -> },
            onTaskActionClick = { _, _ -> },
        )
    }
}
