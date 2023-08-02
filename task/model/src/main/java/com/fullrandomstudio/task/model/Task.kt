package com.fullrandomstudio.task.model

import java.time.ZonedDateTime

data class Task(
    val id: Long,
    val name: String,
    val description: String,
    val category: TaskCategory,
    val scheduled: Boolean,
    val creationDate: ZonedDateTime,
    val scheduleDate: ZonedDateTime?,
    val finishDate: ZonedDateTime?,
    val taskAlarm: TaskAlarm?,
    val softDeleted: Boolean,
) {
    val hasAlarm: Boolean = taskAlarm != null
    val isFinished: Boolean = finishDate != null
    val isScheduled: Boolean = scheduleDate != null
    val isGeneral: Boolean = scheduleDate == null

    companion object {
        fun empty(scheduled: Boolean) = Task(
            name = "",
            description = "",
            category = TaskCategory("", 0, false, 0),
            scheduled = scheduled,
            creationDate = ZonedDateTime.now(),
            scheduleDate = if (scheduled) ZonedDateTime.now() else null,
            finishDate = null,
            taskAlarm = if (scheduled) TaskAlarm(0, 0, ZonedDateTime.now()) else null,
            softDeleted = false,
            id = 0
        )
    }
}
