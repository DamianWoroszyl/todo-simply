package com.fullrandomstudio.todosimply.task.data.repository

import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskAlarm
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getScheduledTasks(dateRange: DateRange): Flow<List<Task>>
    suspend fun saveTask(task: Task): Long
    suspend fun getTask(taskId: Long): Task?
    suspend fun softDeleteTask(taskId: Long)
    suspend fun getTaskName(taskId: Long): String?
    suspend fun undoSoftDeleteTask(taskId: Long)
    suspend fun removeSoftDeletedTasks()
    suspend fun setTaskDone(taskId: Long, done: Boolean)
    suspend fun setTaskAlarm(taskId: Long, taskAlarm: TaskAlarm?)
}
