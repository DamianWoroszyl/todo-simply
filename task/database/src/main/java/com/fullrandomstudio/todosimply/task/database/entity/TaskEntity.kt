package com.fullrandomstudio.todosimply.task.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "task")
data class TaskEntity(
    val title: String,
    val description: String,
    val scheduled: Boolean,
    @ColumnInfo(name = "soft_deleted") val softDeleted: Boolean,
    @ColumnInfo(name = "creation_date") val creationDate: ZonedDateTime,
    @ColumnInfo(name = "schedule_date") val scheduleDate: ZonedDateTime,
    @ColumnInfo(name = "finish_date") val finishDate: ZonedDateTime,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
