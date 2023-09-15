package com.fullrandomstudio.todosimply.task.domain

import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskAlarm
import com.fullrandomstudio.task.model.toUtcSameInstant
import com.fullrandomstudio.todosimply.task.data.repository.TaskCategoryRepository
import com.fullrandomstudio.todosimply.task.data.repository.TaskRepository
import com.fullrandomstudio.todosimply.task.domain.exception.TaskNotFound
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class PrepareTaskToEditUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskCategoryRepository: TaskCategoryRepository
) {

    suspend operator fun invoke(
        taskEditType: TaskEditType,
        taskId: Long,
        scheduled: Boolean,
        selectedDate: LocalDate?
    ): Result<Task> {

        val task: Task? = taskRepository.getTask(taskId)
        if (task == null && taskEditType != TaskEditType.CREATE) {
            return Result.failure<Task>(TaskNotFound())
        }

        val finalTask: Task = when (taskEditType) {
            TaskEditType.EDIT -> requireNotNull(task)
            TaskEditType.CREATE -> createNewEmptyTask(
                scheduled = scheduled,
                selectedDay = selectedDate
            )

            TaskEditType.DUPLICATE -> createTaskDuplicate(originalTask = requireNotNull(task))
            TaskEditType.MOVE_FROM_GENERAL_TO_SCHEDULED ->
                prepareTaskToMoveToScheduled(originalTask = requireNotNull(task))
        }

        return Result.success(finalTask)
    }

    private suspend fun createNewEmptyTask(
        scheduled: Boolean,
        selectedDay: LocalDate?
    ): Task {
        val scheduleTime: ZonedDateTime? = if (scheduled) createNewTaskDateTime(selectedDay) else null
        return Task(
            name = "",
            description = "",
            category = taskCategoryRepository.getDefaultCategory(),
            scheduled = scheduled,
            creationDateTimeUtc = creationDateUtc(),
            scheduleDateTime = scheduleTime,
            finishDateTimeUtc = null,
            softDeleted = false,
            id = 0,
            taskAlarm = TaskAlarm(0L, requireNotNull(scheduleTime))
        )
    }

    private fun prepareTaskToMoveToScheduled(originalTask: Task): Task {
        val scheduleTime: ZonedDateTime = createNewTaskDateTime()
        return originalTask.copy(
            scheduleDateTime = scheduleTime,
            scheduled = true,
            taskAlarm = TaskAlarm(originalTask.id, scheduleTime)
        )
    }

    private fun createTaskDuplicate(originalTask: Task): Task {
        return originalTask.copy(
            softDeleted = false,
            creationDateTimeUtc = creationDateUtc(),
            finishDateTimeUtc = null,
            id = 0
        )
    }

    private fun creationDateUtc(): LocalDateTime =
        ZonedDateTime.now().toUtcSameInstant().toLocalDateTime()

    private fun createNewTaskDateTime(selectedDate: LocalDate? = null): ZonedDateTime {
        val date = selectedDate ?: LocalDate.now()
        return LocalTime.now()
            .plusHours(SCHEDULE_TASK_OFFSET_HOURS)
            .truncatedTo(ChronoUnit.HOURS)
            .atDate(date)
            .atZone(ZoneId.systemDefault())
    }

    companion object {
        private const val SCHEDULE_TASK_OFFSET_HOURS = 2L
    }
}
