package com.fullrandomstudio.todosimply.task.data.repository

import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.common.coroutine.Dispatcher
import com.fullrandomstudio.todosimply.common.coroutine.TodoSimplyDispatchers.IO
import com.fullrandomstudio.todosimply.task.database.dao.TaskCategoryDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineTaskCategoryRepository @Inject constructor(
    private val taskCategoryDao: TaskCategoryDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : TaskCategoryRepository {

    override suspend fun save(categories: List<TaskCategory>) = withContext(ioDispatcher) {
        taskCategoryDao.insert(
            categories.map {
                it.toEntity()
            }
        )
    }

    override suspend fun anyExists(): Boolean = taskCategoryDao.anyExists()
}
