package com.fullrandomstudio.todosimply.task.database.view

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.fullrandomstudio.todosimply.task.database.entity.TaskAlarmEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskCategoryEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskEntity

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
