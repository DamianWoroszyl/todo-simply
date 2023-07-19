package com.fullrandomstudio.todosimply.task.data.repository

import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getScheduledTasks(timeRange: DateRange): Flow<List<Task>>
    fun saveTask(task: Task): Result<Long>
}
