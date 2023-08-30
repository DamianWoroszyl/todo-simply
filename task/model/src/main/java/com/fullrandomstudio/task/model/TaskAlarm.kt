package com.fullrandomstudio.task.model

import java.time.ZonedDateTime

data class TaskAlarm(
    val id: Long,
    val taskId: Long,
    val alarmDateTime: ZonedDateTime
)
