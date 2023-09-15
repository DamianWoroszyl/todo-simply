package com.fullrandomstudio.task.ui.scheduled.taskslistscreen.effect

import com.fullrandomstudio.core.ui.effect.Effect

sealed interface ScheduledTasksListScreenEffect : Effect

data class DeleteTaskEffect(
    val taskId: Long,
    val taskName: String
) : ScheduledTasksListScreenEffect
