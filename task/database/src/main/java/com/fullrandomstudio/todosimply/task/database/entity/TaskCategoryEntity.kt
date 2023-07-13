package com.fullrandomstudio.todosimply.task.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_category")
data class TaskCategoryEntity(
    val name: String,
    val color: Int,
    val isDefault: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
