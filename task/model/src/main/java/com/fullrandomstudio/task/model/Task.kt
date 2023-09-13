package com.fullrandomstudio.task.model

import java.time.LocalDateTime
import java.time.ZonedDateTime

data class Task(
    val id: Long,
    val name: String,
    val description: String,
    val category: TaskCategory,
    val scheduled: Boolean,
    val creationDateTimeUtc: LocalDateTime,
    val scheduleDateTime: ZonedDateTime?,
    val finishDateTimeUtc: LocalDateTime?,
    val taskAlarm: TaskAlarm?,
    val softDeleted: Boolean,
) {
    fun exists(): Boolean {
        return id > 0L
    }

    val hasAlarm: Boolean = taskAlarm != null
    val isFinished: Boolean = finishDateTimeUtc != null
    val isScheduled: Boolean = scheduleDateTime != null
    val isGeneral: Boolean = scheduleDateTime == null

    companion object {
        fun empty(scheduled: Boolean) = Task(
            name = "",
            description = "",
            category = TaskCategory("", 0, false, 0),
            scheduled = scheduled,
            creationDateTimeUtc = LocalDateTime.now(),
            scheduleDateTime = if (scheduled) ZonedDateTime.now() else null,
            finishDateTimeUtc = null,
            taskAlarm = if (scheduled) TaskAlarm(0, 0, ZonedDateTime.now()) else null,
            softDeleted = false,
            id = 0
        )
    }
}
