package com.fullrandomstudio.task.ui.list.item

import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.todosimply.task.data.config.TaskAction

internal data class TaskListItemUiState(
    val task: Task,
    val taskActions: List<TaskAction>,
    val expanded: Boolean
) : TasksListItem
