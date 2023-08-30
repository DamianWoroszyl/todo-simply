package com.fullrandomstudio.todosimply.task.domain

import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.todosimply.task.data.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduledTasks @Inject constructor(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(dateRange: DateRange): Flow<List<Task>> {
        return taskRepository.getScheduledTasks(dateRange)
    }
}
