package com.fullrandomstudio.todosimply.task.domain

import com.fullrandomstudio.todosimply.task.data.repository.TaskRepository
import javax.inject.Inject

class SetTaskDoneUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(taskId: Long, done: Boolean) {
        return taskRepository.setTaskDone(taskId, done)
    }
}
