package com.fullrandomstudio.todosimply.task.data.repository

import com.fullrandomstudio.task.model.TaskCategory

interface TaskCategoryRepository {

    suspend fun anyExists(): Boolean
    suspend fun save(categories: List<TaskCategory>)
}
