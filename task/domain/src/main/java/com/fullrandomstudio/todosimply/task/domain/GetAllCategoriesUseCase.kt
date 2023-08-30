package com.fullrandomstudio.todosimply.task.domain

import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.task.data.repository.TaskCategoryRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val taskCategoryRepository: TaskCategoryRepository
) {

    suspend operator fun invoke(): List<TaskCategory> {
        return taskCategoryRepository.getAll()
    }
}
