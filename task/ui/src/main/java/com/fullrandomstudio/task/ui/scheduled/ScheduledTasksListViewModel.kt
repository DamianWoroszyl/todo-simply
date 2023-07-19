package com.fullrandomstudio.task.ui.scheduled

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.ui.list.item.TaskListItemUiState
import com.fullrandomstudio.task.ui.list.item.TasksListItem
import com.fullrandomstudio.todosimply.task.data.config.TaskAction
import com.fullrandomstudio.todosimply.task.data.repository.TaskRepository
import com.fullrandomstudio.todosimply.utilandroid.stateInViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class ScheduledTasksListViewModel @AssistedInject constructor(
    private val taskRepository: TaskRepository,
    private val taskUiStateFactory: TaskUiStateFactory,
    @Assisted private val dateRange: DateRange
) : ViewModel() {

    private val _expandedTask: MutableStateFlow<Long> = MutableStateFlow(-1L)
    val items: StateFlow<List<TasksListItem>> =
        taskRepository.getScheduledTasks(dateRange)
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
        TODO("Not yet implemented")
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
