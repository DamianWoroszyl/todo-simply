package com.fullrandomstudio.todosimply.task.domain

import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.todosimply.task.data.repository.TaskRepository
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(task: Task): Long {
        return taskRepository.saveTask(task)
    }
}
