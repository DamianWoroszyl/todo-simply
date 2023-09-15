package com.fullrandomstudio.todosimply.task.data.repository

import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskAlarm
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.task.database.entity.TaskAlarmEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskCategoryEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskEntity
import com.fullrandomstudio.todosimply.task.database.view.TaskFullView

fun TaskFullView.toDomain(): Task = Task(
    name = task.name,
    description = task.description,
    category = category.toDomain(),
    scheduled = task.scheduled,
    creationDateTimeUtc = task.creationDateTimeUtc,
    scheduleDateTime = task.scheduleDateTime,
    finishDateTimeUtc = task.finishDateTimeUtc,
    taskAlarm = taskAlarm?.toDomain(),
    softDeleted = task.softDeleted,
    id = task.id,
)

fun TaskAlarmEntity.toDomain(): TaskAlarm = TaskAlarm(
    taskId = taskId,
    alarmDateTime = dateTime,
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

internal fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        name = this.name,
        description = this.description,
        scheduled = this.scheduled,
        softDeleted = this.softDeleted,
        creationDateTimeUtc = this.creationDateTimeUtc,
        scheduleDateTime = this.scheduleDateTime,
        finishDateTimeUtc = this.finishDateTimeUtc,
        categoryId = this.category.id,
        id = this.id,
    )
}

internal fun TaskAlarm.toEntity(taskId: Long): TaskAlarmEntity {
    return TaskAlarmEntity(
        taskId = taskId,
        dateTime = alarmDateTime
    )
}
