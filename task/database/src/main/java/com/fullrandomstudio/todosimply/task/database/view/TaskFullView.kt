package com.fullrandomstudio.todosimply.task.database.view

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.task.database.entity.TaskAlarmEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskCategoryEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskEntity
import com.fullrandomstudio.todosimply.task.database.entity.toDomain

//todo dw test
@DatabaseView(
    value =
    """
        SELECT 
            task.* ,
            task_category.name AS task_category_name,
            task_category.id AS task_category_id,
            task_category.color AS task_category_color
        FROM task
        INNER JOIN task_category ON task.category_id = task_category.id 
        LEFT OUTER JOIN task_alarm AS ta ON task.id = ta.task_id
        """
)
data class TaskFullView(
    @Embedded val task: TaskEntity,
    @Embedded(prefix = "task_category_") val category: TaskCategoryEntity?,
    @Embedded(prefix = "alarm_") val taskAlarm: TaskAlarmEntity?,
)

fun TaskFullView.toDomain(defaultCategory: TaskCategory): Task = Task(
    title = task.title,
    description = task.description,
    category = category?.toDomain() ?: defaultCategory,
    scheduled = task.scheduled,
    creationDate = task.creationDate,
    scheduleDate = task.scheduleDate,
    finishDate = task.finishDate,
    taskAlarm = taskAlarm?.toDomain(),
    softDeleted = task.softDeleted,
    id = task.id,
)
