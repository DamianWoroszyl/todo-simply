package com.fullrandomstudio.todosimply.task.data.init

import android.app.Application
import com.fullrandomstudio.initializer.AppInitializer
import com.fullrandomstudio.todosimply.task.data.repository.TaskRepository
import javax.inject.Inject

class SoftDeletedTaskHandler @Inject constructor(
    private val taskRepository: TaskRepository
) : AppInitializer {

    override suspend fun initialize(application: Application) {
        taskRepository.removeSoftDeletedTasks()
    }
}
