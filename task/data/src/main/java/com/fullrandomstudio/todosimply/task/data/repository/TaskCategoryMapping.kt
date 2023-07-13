package com.fullrandomstudio.todosimply.task.data.repository

import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.task.database.entity.TaskCategoryEntity

internal fun TaskCategory.toEntity(): TaskCategoryEntity = TaskCategoryEntity(
    name = name,
    color = color,
    isDefault = isDefault,
    id = id,
)

internal fun TaskCategoryEntity.toDomain(): TaskCategory = TaskCategory(
    name = name,
    color = color,
    isDefault = isDefault,
    id = id,
)
