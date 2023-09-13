package com.fullrandomstudio.task.ui.scheduled.taskslistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fullrandomstudio.core.ui.effect.EffectStateFlow
import com.fullrandomstudio.core.ui.navigation.NavigationStateFlow
import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.ui.common.EditTask
import com.fullrandomstudio.task.ui.edit.TaskEditArgs
import com.fullrandomstudio.task.ui.list.item.TaskAction
import com.fullrandomstudio.task.ui.list.item.TaskListItemUiState
import com.fullrandomstudio.task.ui.list.item.TasksListItem
import com.fullrandomstudio.task.ui.scheduled.effect.DeleteTaskEffect
import com.fullrandomstudio.todosimply.task.domain.DeleteTaskUseCase
import com.fullrandomstudio.todosimply.task.domain.GetScheduledTasksUseCase
import com.fullrandomstudio.todosimply.task.domain.SetTaskDoneUseCase
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
    private val getScheduledTasks: GetScheduledTasksUseCase,
    private val setTaskDoneUseCase: SetTaskDoneUseCase,
    val navigationStateFlow: NavigationStateFlow,
    val effectStateFlow: EffectStateFlow,
    @Assisted private val dateRange: DateRange
) : ViewModel() {

    //todo dw finished here - think about moving expanded to composition, we dont want it to be remembered probably?
    private val _expandedTask: MutableStateFlow<Long> = MutableStateFlow(-1L)

    val items: StateFlow<List<TasksListItem>> = getScheduledTasks(dateRange)
        .combine(_expandedTask, ::Pair)
        .map { taskUiStateFactory.toUiStates(it.first, it.second) }
        .stateInViewModel(this, emptyList())

    fun onTaskClick(taskId: Long) {
        _expandedTask.update { if (it == taskId) -1 else taskId }
    }

    fun onTaskDoneClick(taskId: Long, isFinished: Boolean) {
        viewModelScope.launch {
            setTaskDoneUseCase(taskId, !isFinished)
        }
    }

    fun onToggleAlarm(taskId: Long) {
        requireNotNull(
            items.value.find { it is TaskListItemUiState && it.task.id == taskId }
        ).let {
            val task = it as TaskListItemUiState
            if (task.task.isFinished) {
                // ignore
            } else {
            // taskRepository.set
                TODO("Not yet implemented")
            }
        }
    }

    fun onTaskActionClick(taskId: Long, taskAction: TaskAction) {
        when (taskAction) {
            TaskAction.DELETE -> deleteTask(taskId)

            TaskAction.DUPLICATE -> navigationStateFlow.navigate(
                EditTask(
                    TaskEditArgs(
                        taskEditType = TaskEditType.DUPLICATE,
                        scheduled = true,
                        taskId = taskId,
                    )
                )
            )

            TaskAction.GENERAL_TO_SCHEDULED -> navigationStateFlow.navigate(
                EditTask(
                    TaskEditArgs(
                        taskEditType = TaskEditType.MOVE_FROM_GENERAL_TO_SCHEDULED,
                        scheduled = true,
                        taskId = taskId,
                    )
                )
            )

            TaskAction.EDIT -> navigationStateFlow.navigate(
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
        viewModelScope.launch {
            val result = deleteTaskUseCase.invoke(taskId)
            _expandedTask.value = -1L
            effectStateFlow.emit(
                DeleteTaskEffect(
                    taskId = taskId,
                    taskName = result.second
                )
            )
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
