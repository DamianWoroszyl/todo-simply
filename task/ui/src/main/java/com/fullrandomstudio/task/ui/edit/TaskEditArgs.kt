@file:Suppress("ConstPropertyName", "TopLevelPropertyNaming")

package com.fullrandomstudio.task.ui.edit

import androidx.lifecycle.SavedStateHandle
import com.fullrandomstudio.todosimply.task.domain.TaskEditType
import java.lang.Long.parseLong
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskEditArgs(
    val taskEditType: TaskEditType,
    val scheduled: Boolean,
    val taskId: Long? = null,
    val selectedDate: LocalDate? = null
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        taskEditType = TaskEditType.valueOf(
            requireNotNull(
                savedStateHandle.get<String>(argTaskEditType)
            ).uppercase()
        ),
        scheduled = when (
            TaskType.valueOf(
                requireNotNull(
                    savedStateHandle.get<String>(argTaskType)
                ).uppercase()
            )
        ) {
            TaskType.SCHEDULED -> true
            TaskType.GENERAL -> false
        },
        taskId = savedStateHandle.get<String>(argTaskId)?.let { parseLong(it) },
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
