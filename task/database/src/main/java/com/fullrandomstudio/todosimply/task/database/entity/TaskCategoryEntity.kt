package com.fullrandomstudio.todosimply.task.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fullrandomstudio.task.model.TaskCategory

@Entity(tableName = "task_category")
data class TaskCategoryEntity(
    val name: String,
    val color: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)

fun TaskCategoryEntity.toDomain(): TaskCategory = TaskCategory(
    name = name,
    color = color,
    id = id,
)
