package com.fullrandomstudio.task.model

import java.time.ZonedDateTime

data class Task(
    val title: String,
    val description: String,
    val category: TaskCategory,
    val scheduled: Boolean,
    val creationDate: ZonedDateTime,
    val scheduleDate: ZonedDateTime?,
    val finishDate: ZonedDateTime?,
    val taskAlarm: TaskAlarm?,
    val softDeleted: Boolean = false,
    val id: Long = 0,
) {
    val hasAlarm: Boolean = taskAlarm != null
    val isFinished: Boolean = finishDate != null
    val isScheduled: Boolean = scheduleDate != null
    val isGeneral: Boolean = scheduleDate == null
}