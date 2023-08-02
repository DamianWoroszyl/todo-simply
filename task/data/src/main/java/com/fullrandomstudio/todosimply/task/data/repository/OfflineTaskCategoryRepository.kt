package com.fullrandomstudio.todosimply.task.data.repository

import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.common.coroutine.Dispatcher
import com.fullrandomstudio.todosimply.common.coroutine.TodoSimplyDispatchers.IO
import com.fullrandomstudio.todosimply.common.resources.ResourceProvider
import com.fullrandomstudio.todosimply.task.data.R
import com.fullrandomstudio.todosimply.task.database.dao.TaskCategoryDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineTaskCategoryRepository @Inject constructor(
    private val taskCategoryDao: TaskCategoryDao,
    private val resourceProvider: ResourceProvider,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : TaskCategoryRepository {

    override suspend fun getAll(): List<TaskCategory> = withContext(ioDispatcher) {
        taskCategoryDao.getAll().map { it.toDomain() }
    }

    override suspend fun save(categories: List<TaskCategory>) = withContext(ioDispatcher) {
        taskCategoryDao.insert(
            categories.map {
                it.toEntity()
            }
        )
    }

    override suspend fun anyExists(): Boolean = taskCategoryDao.anyExists()

    override suspend fun getDefaultCategory(): TaskCategory {
        val defaultCategory: TaskCategory? = taskCategoryDao.getDefault()?.toDomain()

        return defaultCategory ?: createAndSaveDefaultCategoryIfDoesntExist()
    }

    override suspend fun createAndSaveDefaultCategoryIfDoesntExist(): TaskCategory {
        return taskCategoryDao.getDefault()?.toDomain() ?: TaskCategory(
            name = resourceProvider.getString(R.string.base_category_default),
            color = resourceProvider.getIntArray(R.array.category_colors_grey)[DEFAULT_CATEGORY_COLOR_INDEX],
            isDefault = true,
            id = 0
        ).run {
            taskCategoryDao.insert(listOf(this.toEntity()))
            requireNotNull(taskCategoryDao.getDefault()).toDomain()
        }
    }

    companion object {
        private const val DEFAULT_CATEGORY_COLOR_INDEX: Int = 2
    }
}
