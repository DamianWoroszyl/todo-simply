package com.fullrandomstudio.todosimply.task.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Entity(tableName = "task")
data class TaskEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "scheduled") val scheduled: Boolean,
    @ColumnInfo(name = "soft_deleted") val softDeleted: Boolean,
    @ColumnInfo(name = "creation_date_time_utc") val creationDateTimeUtc: LocalDateTime,
    @ColumnInfo(name = "schedule_date_time") val scheduleDateTime: ZonedDateTime?,
    @ColumnInfo(name = "finish_date_time_utc") val finishDateTimeUtc: LocalDateTime?,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
