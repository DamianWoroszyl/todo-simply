package com.fullrandomstudio.todosimply.task.data.repository

import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getScheduledTasks(dateRange: DateRange): Flow<List<Task>>
    suspend fun saveTask(task: Task): Long
    suspend fun getTask(taskId: Long): Task?
    suspend fun deleteTask(taskId: Long)
}
