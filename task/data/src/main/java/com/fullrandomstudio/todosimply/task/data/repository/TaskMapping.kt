package com.fullrandomstudio.todosimply.task.data.repository

import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskAlarm
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.task.database.entity.TaskAlarmEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskCategoryEntity
import com.fullrandomstudio.todosimply.task.database.view.TaskFullView

fun TaskFullView.toDomain(): Task = Task(
    name = task.name,
    description = task.description,
    category = category.toDomain(),
    scheduled = task.scheduled,
    creationDate = task.creationDate,
    scheduleDateTime = task.scheduleDate,
    finishDate = task.finishDate,
    taskAlarm = taskAlarm?.toDomain(),
    softDeleted = task.softDeleted,
    id = task.id,
)

fun TaskAlarmEntity.toDomain(): TaskAlarm = TaskAlarm(
    id = id,
    taskId = taskId,
    alarmDate = dateTime,
)

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
