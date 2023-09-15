package com.fullrandomstudio.todosimply.task.domain

import com.fullrandomstudio.todosimply.task.data.repository.TaskRepository
import javax.inject.Inject

class UndoTaskDeleteUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(taskId: Long) {
        taskRepository.undoSoftDeleteTask(taskId)
    }
}
