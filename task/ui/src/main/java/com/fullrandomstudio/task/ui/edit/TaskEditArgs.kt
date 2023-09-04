package com.fullrandomstudio.task.ui.edit

import androidx.lifecycle.SavedStateHandle
import com.fullrandomstudio.todosimply.task.domain.TaskEditType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskEditArgs(
    val taskEditType: TaskEditType,
    val scheduled: Boolean,
    val taskId: Long = -1L,
    val selectedDate: LocalDate? = null
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        taskEditType = requireNotNull(savedStateHandle.get<TaskEditType>(argTaskEditType)),
        scheduled = when (requireNotNull(savedStateHandle.get<TaskType>(argTaskType))) {
            TaskType.SCHEDULED -> true
            TaskType.GENERAL -> false
        },
        taskId = savedStateHandle.get<Long>(argTaskId) ?: -1L,
        selectedDate = savedStateHandle.get<String>(argSelectedDate)?.let {
            LocalDate.parse(it, selectedDateFormatter)
        }
    )

    companion object {
        val selectedDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        const val argTaskId = "taskId"
        const val argTaskType = "taskType"
        const val argTaskEditType = "taskEditType"
        const val argSelectedDate = "selectedDate"
    }
}

enum class TaskType {
    SCHEDULED, GENERAL
}
