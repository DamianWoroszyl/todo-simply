package com.fullrandomstudio.todosimply.task.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context,
    ): TaskDatabase = Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        "task-database",
    ).build()

    @Provides
    @Singleton
    fun provideTaskDao(
        taskDatabase: TaskDatabase
    ) = taskDatabase.taskDao()

    @Provides
    @Singleton
    fun provideTaskAlarmDao(
        taskDatabase: TaskDatabase
    ) = taskDatabase.taskAlarmDao()

    @Provides
    @Singleton
    fun provideTaskCategoryDao(
        taskDatabase: TaskDatabase
    ) = taskDatabase.taskCategoryDao()
}
