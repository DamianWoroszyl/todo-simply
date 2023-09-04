package com.fullrandomstudio.task.ui.scheduled.effect

import com.fullrandomstudio.core.ui.effect.Effect

data class DeleteTaskEffect(
    val taskId: Long,
    val taskName: String
) : Effect
