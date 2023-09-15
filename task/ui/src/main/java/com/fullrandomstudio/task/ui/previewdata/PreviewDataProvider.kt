package com.fullrandomstudio.task.ui.previewdata

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.fullrandomstudio.designsystem.theme.md_theme_light_secondary
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.task.ui.list.item.TaskAction
import com.fullrandomstudio.task.ui.list.item.TaskListItemUiState
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Composable
internal fun previewDoneTaskUiState(): TaskListItemUiState = TaskListItemUiState(
    task = Task(
        name = "Some task",
        description = "Some task description",
        creationDateTimeUtc = LocalDateTime.now().minusDays(5),
        scheduleDateTime = ZonedDateTime.now().plusDays(5),
        finishDateTimeUtc = LocalDateTime.now(),
        scheduled = true,
        category = TaskCategory(
            name = "Home",
            color = md_theme_light_secondary.toArgb(),
            isDefault = false,
            id = 0,
        ),
        taskAlarm = com.fullrandomstudio.task.model.TaskAlarm(1L, ZonedDateTime.now().plusDays(5)),
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
        creationDateTimeUtc = LocalDateTime.now().minusDays(5),
        scheduleDateTime = ZonedDateTime.now().plusDays(5),
        finishDateTimeUtc = null,
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
