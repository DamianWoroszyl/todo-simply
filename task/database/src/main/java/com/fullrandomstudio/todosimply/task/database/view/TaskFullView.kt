package com.fullrandomstudio.todosimply.task.database.view

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.fullrandomstudio.todosimply.task.database.entity.TaskAlarmEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskCategoryEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskEntity

// todo dw test
@DatabaseView(
    viewName = "task_full_view",
    value =
    """
        SELECT 
            task.* ,
            task_category.name AS task_category_name,
            task_category.id AS task_category_id,
            task_category.color AS task_category_color,
            task_category.is_default AS task_category_is_default,
            task_alarm.task_id AS alarm_task_id,
            task_alarm.date_time AS alarm_date_time,
            task_alarm.id AS alarm_id
        FROM task
        INNER JOIN task_category ON task.category_id = task_category.id 
        LEFT OUTER JOIN task_alarm ON task.id = task_alarm.task_id
        """
)
data class TaskFullView(
    @Embedded val task: TaskEntity,
    @Embedded(prefix = "task_category_") val category: TaskCategoryEntity,
    @Embedded(prefix = "alarm_") val taskAlarm: TaskAlarmEntity?,
)
