package com.fullrandomstudio.todosimply.task.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fullrandomstudio.task.model.TaskAlarm
import java.time.ZonedDateTime

@Entity(tableName = "task_alarm")
data class TaskAlarmEntity(
    @ColumnInfo(name = "task_id") val taskId: Long,
    @ColumnInfo(name = "alarm_date_time") val alarmDateTime: ZonedDateTime,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)


fun TaskAlarmEntity.toDomain(): TaskAlarm = TaskAlarm(
    taskId = taskId,
    alarmDate = alarmDateTime,
)
