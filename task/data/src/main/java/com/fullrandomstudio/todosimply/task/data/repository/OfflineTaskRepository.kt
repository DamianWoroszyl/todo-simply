package com.fullrandomstudio.todosimply.task.data.repository

import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.model.ISO_ZONED_DATE_TIME_FORMATTER
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskAlarm
import com.fullrandomstudio.task.model.toUtcSameInstant
import com.fullrandomstudio.todosimply.common.coroutine.ApplicationCoroutineScope
import com.fullrandomstudio.todosimply.common.coroutine.Dispatcher
import com.fullrandomstudio.todosimply.common.coroutine.TodoSimplyDispatchers.IO
import com.fullrandomstudio.todosimply.task.database.dao.TaskAlarmDao
import com.fullrandomstudio.todosimply.task.database.dao.TaskDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class OfflineTaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskAlarmDao: TaskAlarmDao,
    @ApplicationCoroutineScope private val appCoroutineScope: CoroutineScope,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : TaskRepository {

    override fun getScheduledTasks(dateRange: DateRange): Flow<List<Task>> {
        val start = dateRange.start.atStartOfDay(ZoneId.systemDefault())
            .format(ISO_ZONED_DATE_TIME_FORMATTER)
        val end = dateRange.end.atStartOfDay(ZoneId.systemDefault())
            .plusDays(1).minusSeconds(1)
            .format(ISO_ZONED_DATE_TIME_FORMATTER)
        return taskDao.get(start, end).map { data ->
            data.map { it.toDomain() }
        }
    }

    override suspend fun saveTask(task: Task): Long = withContext(ioDispatcher) {
        val taskEntity = task.toEntity()
        val taskId: Long = appCoroutineScope.async {
            val taskId = taskDao.insert(taskEntity)
            setTaskAlarm(taskId, task.taskAlarm)
            taskId
        }.await()

        return@withContext taskId
    }

    override suspend fun getTask(taskId: Long): Task? = withContext(ioDispatcher) {
        taskDao.getFullView(taskId)?.toDomain()
    }

    override suspend fun getTaskName(taskId: Long): String? = withContext(ioDispatcher) {
        return@withContext taskDao.getName(taskId)
    }

    override suspend fun softDeleteTask(taskId: Long) = withContext(ioDispatcher) {
        appCoroutineScope.launch {
            taskDao.setSoftDeleted(taskId, true)
        }.join()
    }

    override suspend fun undoSoftDeleteTask(taskId: Long) = withContext(ioDispatcher) {
        appCoroutineScope.launch {
            taskDao.setSoftDeleted(taskId, false)
        }.join()
    }

    override suspend fun removeSoftDeletedTasks() {
        appCoroutineScope.launch {
            val softDeletedTaskIds = taskDao.getSoftDeletedIds()
            taskDao.remove(softDeletedTaskIds)
            taskAlarmDao.removeByTaskId(softDeletedTaskIds)
        }.join()
    }

    override suspend fun setTaskDone(taskId: Long, done: Boolean) {
        appCoroutineScope.launch {
            val finishDate = if (done) {
                ZonedDateTime.now().toUtcSameInstant()
            } else {
                null
            }?.toLocalDateTime()

            taskDao.setTaskFinishDateTime(taskId, finishDate)
        }.join()
    }

    override suspend fun setTaskAlarm(taskId: Long, taskAlarm: TaskAlarm?) {
        appCoroutineScope.launch {
            taskAlarmDao.deleteForTask(taskId)
            taskAlarm?.toEntity(taskId)?.let { taskAlarmDao.insert(it) }
            //todo schedule / reschedule task alarm
        }.join()
    }
}
