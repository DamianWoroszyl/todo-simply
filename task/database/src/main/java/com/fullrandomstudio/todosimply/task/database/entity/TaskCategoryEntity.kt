package com.fullrandomstudio.todosimply.task.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_category")
data class TaskCategoryEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "color") val color: Int,
    @ColumnInfo(name = "is_default") val isDefault: Boolean,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0
)
