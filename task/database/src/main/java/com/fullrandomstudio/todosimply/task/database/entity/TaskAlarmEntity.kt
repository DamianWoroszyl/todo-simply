package com.fullrandomstudio.todosimply.task.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "task_alarm")
data class TaskAlarmEntity(
    @ColumnInfo(name = "task_id") val taskId: Long,
    @ColumnInfo(name = "date_time") val dateTime: ZonedDateTime,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
