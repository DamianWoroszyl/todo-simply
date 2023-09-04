package com.fullrandomstudio.task.ui.scheduled

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fullrandomstudio.core.ui.Navigator
import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.ui.common.EditTask
import com.fullrandomstudio.task.ui.edit.TaskEditArgs
import com.fullrandomstudio.task.ui.list.item.TaskListItemUiState
import com.fullrandomstudio.task.ui.list.item.TasksListItem
import com.fullrandomstudio.todosimply.task.data.config.TaskAction
import com.fullrandomstudio.todosimply.task.domain.DeleteTaskUseCase
import com.fullrandomstudio.todosimply.task.domain.GetScheduledTasks
import com.fullrandomstudio.todosimply.task.domain.TaskEditType
import com.fullrandomstudio.todosimply.utilandroid.stateInViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScheduledTasksListViewModel @AssistedInject constructor(
    private val taskUiStateFactory: TaskUiStateFactory,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getScheduledTasks: GetScheduledTasks,
    val navigator: Navigator,
    @Assisted private val dateRange: DateRange
) : ViewModel() {

    private val _expandedTask: MutableStateFlow<Long> = MutableStateFlow(-1L)

    // todo dw move to use case?
    val items: StateFlow<List<TasksListItem>> = getScheduledTasks(dateRange)
            .combine(_expandedTask, ::Pair)
            .map { taskUiStateFactory.toUiStates(it.first, it.second) }
            .stateInViewModel(this, emptyList())

    fun onTaskClick(taskId: Long) {
        _expandedTask.update { if (it == taskId) -1 else taskId }
    }

    fun onToggleAlarm(taskId: Long) {
        items.value
            .find { it is TaskListItemUiState && it.task.id == taskId }
//            .let { taskRepository.set }
        TODO("Not yet implemented")
    }

    fun onTaskActionClick(taskId: Long, taskAction: TaskAction) {
        when (taskAction) {
            TaskAction.DELETE -> deleteTask(taskId)

            TaskAction.DUPLICATE -> navigator.navigate(
                EditTask(
                    TaskEditArgs(
                        taskEditType = TaskEditType.DUPLICATE,
                        scheduled = true,
                        taskId = taskId,
                    )
                )
            )

            TaskAction.GENERAL_TO_SCHEDULED -> navigator.navigate(
                EditTask(
                    TaskEditArgs(
                        taskEditType = TaskEditType.MOVE_FROM_GENERAL_TO_SCHEDULED,
                        scheduled = true,
                        taskId = taskId,
                    )
                )
            )

            TaskAction.EDIT -> navigator.navigate(
                EditTask(
                    TaskEditArgs(
                        taskEditType = TaskEditType.EDIT,
                        scheduled = true,
                        taskId = taskId,
                    )
                )
            )
        }
    }

    private fun deleteTask(taskId: Long) {
        // todo in later task show undo snackbar
        viewModelScope.launch {
            deleteTaskUseCase.invoke(taskId)
        }
    }

    @AssistedFactory
    interface ScheduledTasksListViewModelAssistedFactory {
        fun create(dateRange: DateRange): ScheduledTasksListViewModel
    }

    companion object {
        fun provideViewModelFactory(
            scheduledTasksListViewModelAssistedFactory: ScheduledTasksListViewModelAssistedFactory,
            dateRange: DateRange
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    scheduledTasksListViewModelAssistedFactory.create(dateRange) as T
            }
        }
    }
}
