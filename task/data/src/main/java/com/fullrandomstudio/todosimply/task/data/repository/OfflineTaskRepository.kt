package com.fullrandomstudio.todosimply.task.data.repository

import android.graphics.Color
import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.common.coroutine.Dispatcher
import com.fullrandomstudio.todosimply.common.coroutine.TodoSimplyDispatchers.IO
import com.fullrandomstudio.todosimply.task.database.dao.TaskAlarmDao
import com.fullrandomstudio.todosimply.task.database.dao.TaskCategoryDao
import com.fullrandomstudio.todosimply.task.database.dao.TaskDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class OfflineTaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskAlarmDao: TaskAlarmDao,
    private val taskCategoryDao: TaskCategoryDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : TaskRepository {

    override fun getScheduledTasks(dateRange: DateRange): Flow<List<Task>> {
        //todo
        val category = TaskCategory(
            name = "Home",
            color = Color.BLUE,
            isDefault = false,
            id = 0
        )

        val tasks = List(3) {
            val scheduleTime = dateRange.start.atTime(it * 3, 0).atZone(ZoneId.systemDefault())
            Task(
                name = "Some task ${dateRange.start} $it",
                description = "Some task description $scheduleTime",
                creationDate = ZonedDateTime.now().minusDays(5),
                scheduleDate = scheduleTime,
                finishDate = ZonedDateTime.now(),
                scheduled = true,
                category = category,
                taskAlarm = com.fullrandomstudio.task.model.TaskAlarm(0L, 1L, ZonedDateTime.now().plusDays(5)),
                softDeleted = false,
                id = scheduleTime.toEpochSecond(),
            )
        }
        return flowOf(
            tasks
        )
    }

    override suspend fun saveTask(task: Task): Result<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: Long): Task? = withContext(ioDispatcher) {
        taskDao.getFullView(taskId)?.toDomain()
    }
}
