package com.fullrandomstudio.task.ui.scheduled

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.ui.list.item.TaskCategoryMarkerListItem
import com.fullrandomstudio.task.ui.list.item.TaskCategoryMarkerListItemUiState
import com.fullrandomstudio.task.ui.list.item.TaskListItem
import com.fullrandomstudio.task.ui.list.item.TaskListItemUiState
import com.fullrandomstudio.task.ui.list.item.TasksDateMarkerListItem
import com.fullrandomstudio.task.ui.list.item.TasksDateMarkerListItemUiState
import com.fullrandomstudio.task.ui.list.item.TasksListItem
import com.fullrandomstudio.task.ui.previewdata.previewDoneTaskUiState
import com.fullrandomstudio.task.ui.previewdata.previewTodoTaskUiState
import com.fullrandomstudio.todosimply.task.data.constant.TaskAction

@Composable
fun ScheduledTasksListScreen(
    dateRange: DateRange,
    modifier: Modifier = Modifier,
    viewModel: ScheduledTasksListViewModel
) {
    val items by viewModel.items.collectAsStateWithLifecycle()

    ScheduledTasksListScreen(
        items = items,
        onTaskClick = { taskId -> viewModel.onTaskClick(taskId) },
        onToggleAlarm = { taskId -> viewModel.onToggleAlarm(taskId) },
        onTaskActionClick = { taskId, taskAction ->
            viewModel.onTaskActionClick(taskId, taskAction)
        },
        modifier = modifier
    )
}

@Composable
fun ScheduledTasksListScreen(
    items: List<TasksListItem>,
    onTaskClick: (taskId: Long) -> Unit,
    onToggleAlarm: (taskId: Long) -> Unit,
    onTaskActionClick: (taskId: Long, taskAction: TaskAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
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
                        onToggleAlarm = onToggleAlarm,
                        onTaskActionClick = onTaskActionClick,
                    )
                }
            }
        }
    }
}

private fun TasksListItem.contentType(): Int {
    return when (this) {
        is TaskCategoryMarkerListItemUiState -> 1
        is TaskListItemUiState -> 2
        is TasksDateMarkerListItemUiState -> 3
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
            onTaskClick = { _ -> },
            onToggleAlarm = { _ -> },
            onTaskActionClick = { _, _ -> },
        )
    }
}
