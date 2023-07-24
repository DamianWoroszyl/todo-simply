package com.fullrandomstudio.task.ui.scheduled

import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.ui.list.item.TaskListItemUiState
import com.fullrandomstudio.task.ui.list.item.TasksListItem
import com.fullrandomstudio.todosimply.task.data.config.TaskAction
import javax.inject.Inject

class TaskUiStateFactory @Inject constructor() {

    fun toUiStates(
        tasks: List<Task>,
        expandedTaskId: Long?
    ): List<TasksListItem> {
        return tasks.map {
            TaskListItemUiState(
                task = it,
                taskActions = taskActions(it.isFinished, it.scheduled),
                expanded = expandedTaskId == it.id
            )
        }
    }

    private fun taskActions(isFinished: Boolean, isScheduled: Boolean): List<TaskAction> {
        val actions: MutableList<TaskAction> =
            if (isFinished) {
                mutableListOf(
                    TaskAction.DELETE
                )
            } else {
                mutableListOf(
                    TaskAction.DELETE,
                    TaskAction.EDIT,
                    /*TaskAction.DONE*/
                )
            }

        if (!isScheduled && !isFinished) {
            actions.add(TaskAction.GENERAL_TO_SCHEDULED)
        }
        if (isScheduled) {
            actions.add(TaskAction.DUPLICATE)
        }

        actions.sortBy { it.ordinal }

        return actions
    }
}
