package com.fullrandomstudio.todosimply.task.domain

import com.fullrandomstudio.todosimply.task.data.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    /**
     * Returns task id and task name when successfully deleted
     */
    suspend operator fun invoke(taskId: Long): Pair<Long, String> {
        val taskName: String = requireNotNull(taskRepository.getTaskName(taskId))
        taskRepository.softDeleteTask(taskId)
        return taskId to taskName
    }
}
