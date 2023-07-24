package com.fullrandomstudio.task.model

import java.time.ZonedDateTime

data class TaskAlarm(
    val taskId: Long,
    val alarmDate: ZonedDateTime
)
