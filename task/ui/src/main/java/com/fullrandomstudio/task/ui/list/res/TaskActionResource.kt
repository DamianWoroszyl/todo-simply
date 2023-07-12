package com.fullrandomstudio.task.ui.list.res

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fullrandomstudio.todosimply.task.data.constant.TaskAction
import com.fullrandomstudio.todosimply.task.ui.R

data class TaskActionResources(
    @DrawableRes val drawableRes: Int,
    @StringRes val stringRes: Int
)

fun taskActionResource(taskAction: TaskAction): TaskActionResources {
    return when (taskAction) {
        TaskAction.DELETE ->
            TaskActionResources(R.drawable.ic_delete, R.string.task_action_delete)

        TaskAction.DUPLICATE ->
            TaskActionResources(R.drawable.ic_copy, R.string.task_action_duplicate)

        TaskAction.GENERAL_TO_SCHEDULED ->
            TaskActionResources(R.drawable.ic_general_to_scheduled, R.string.task_action_schedule)

        TaskAction.EDIT ->
            TaskActionResources(R.drawable.ic_edit, R.string.task_action_edit)
    }
}
