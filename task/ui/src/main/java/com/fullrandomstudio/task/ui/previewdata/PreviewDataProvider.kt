package com.fullrandomstudio.task.ui.previewdata

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.fullrandomstudio.designsystem.theme.md_theme_light_secondary
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.task.ui.list.item.TaskListItemUiState
import com.fullrandomstudio.todosimply.task.data.config.TaskAction
import java.time.ZonedDateTime

@Composable
internal fun previewDoneTaskUiState(): TaskListItemUiState = TaskListItemUiState(
    task = Task(
        name = "Some task",
        description = "Some task description",
        creationDate = ZonedDateTime.now().minusDays(5),
        scheduleDate = ZonedDateTime.now().plusDays(5),
        finishDate = ZonedDateTime.now(),
        scheduled = true,
        category = TaskCategory(
            name = "Home",
            color = md_theme_light_secondary.toArgb(),
            isDefault = false,
            id = 0,
        ),
        taskAlarm = com.fullrandomstudio.task.model.TaskAlarm(0L, 1L, ZonedDateTime.now().plusDays(5)),
        softDeleted = false,
        id = 1L,
    ),
    taskActions = TaskAction.values().toList(),
    expanded = true
)

@Composable
internal fun previewTodoTaskUiState(): TaskListItemUiState = TaskListItemUiState(
    task = Task(
        name = "Some task",
        description = "Some task description",
        creationDate = ZonedDateTime.now().minusDays(5),
        scheduleDate = ZonedDateTime.now().plusDays(5),
        finishDate = null,
        scheduled = true,
        category = TaskCategory(
            name = "Home",
            color = md_theme_light_secondary.toArgb(),
            isDefault = false,
            id = 0,
        ),
        taskAlarm = null,
        softDeleted = false,
        id = 2L,
    ),
    taskActions = TaskAction.values().toList(),
    expanded = true
)
