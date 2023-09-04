package com.fullrandomstudio.todosimply.task.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fullrandomstudio.todosimply.task.database.dao.TaskAlarmDao
import com.fullrandomstudio.todosimply.task.database.dao.TaskCategoryDao
import com.fullrandomstudio.todosimply.task.database.dao.TaskDao
import com.fullrandomstudio.todosimply.task.database.entity.TaskAlarmEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskCategoryEntity
import com.fullrandomstudio.todosimply.task.database.entity.TaskEntity
import com.fullrandomstudio.todosimply.task.database.time.LocalDateTimeConverter
import com.fullrandomstudio.todosimply.task.database.time.ZoneIdConverter
import com.fullrandomstudio.todosimply.task.database.time.ZonedDateTimeConverter
import com.fullrandomstudio.todosimply.task.database.view.TaskFullView

@Database(
    entities = [
        TaskEntity::class,
        TaskAlarmEntity::class,
        TaskCategoryEntity::class
    ],
    views = [
        TaskFullView::class
    ],
    version = 1
)
@TypeConverters(
    ZonedDateTimeConverter::class,
    LocalDateTimeConverter::class,
    ZoneIdConverter::class
)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskCategoryDao(): TaskCategoryDao
    abstract fun taskAlarmDao(): TaskAlarmDao
}
