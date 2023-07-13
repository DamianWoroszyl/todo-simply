package com.fullrandomstudio.task.model

data class TaskCategory(
    val name: String,
    val color: Int,
    val isDefault: Boolean = false,
    val id: Long = 0
)
