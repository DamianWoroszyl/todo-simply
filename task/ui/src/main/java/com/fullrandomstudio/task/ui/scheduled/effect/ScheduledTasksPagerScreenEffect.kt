package com.fullrandomstudio.task.ui.scheduled.effect

import com.fullrandomstudio.core.ui.effect.Effect

sealed interface ScheduledTasksPagerScreenEffect : Effect

data class DeleteTaskEffect(
    val taskId: Long,
    val taskName: String
) : ScheduledTasksPagerScreenEffect
