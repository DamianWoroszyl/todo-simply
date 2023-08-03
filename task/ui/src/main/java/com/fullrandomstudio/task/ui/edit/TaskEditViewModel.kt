package com.fullrandomstudio.task.ui.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskAlarm
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.task.data.repository.TaskCategoryRepository
import com.fullrandomstudio.todosimply.task.domain.PrepareTaskToEditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val prepareTaskToEditUseCase: PrepareTaskToEditUseCase,
    private val taskCategoryRepository: TaskCategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args: TaskEditArgs = TaskEditArgs(savedStateHandle)

    val taskName: MutableState<String> = mutableStateOf("")
    val taskDescription: MutableState<String> = mutableStateOf("")

    private val _task: MutableStateFlow<Task> = MutableStateFlow(Task.empty(args.scheduled))
    private val _categoryDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _categories: MutableStateFlow<List<TaskCategory>> = MutableStateFlow(emptyList())

    val task: StateFlow<Task> = _task
    val categoryDialogVisible: StateFlow<Boolean> = _categoryDialogVisible
    val taskCategories: StateFlow<List<TaskCategory>> = _categories

    init {
        viewModelScope.launch {
            //todo dw wrap in methods
            _categories.value = taskCategoryRepository.getAll()

            //todo dw wrap in methods
            val taskResult: Result<Task> = prepareTaskToEditUseCase(
                taskEditType = args.taskEditType,
                taskId = args.taskId,
                scheduled = args.scheduled,
                selectedDate = args.selectedDate,
            )

            taskResult.fold(
                {
                    _task.value = it
                },
                {
                    //todo dw show error and go back
                }
            )
        }
    }

    fun onNameChange(value: String) {
        taskName.value = value
    }

    fun onDescriptionChange(value: String) {
        taskDescription.value = value
    }

    fun onSaveClick() {
        TODO("Not yet implemented")
    }

    fun onDoneClick() {
        TODO("Not yet implemented")
    }

    fun onAlarmChange() {
        _task.update {
            val newAlarm: TaskAlarm? = if (it.hasAlarm) {
                null
            } else {
                TaskAlarm(0, it.id, requireNotNull(it.scheduleDate))
            }

            it.copy(taskAlarm = newAlarm)
        }
    }

    fun onCategorySelectDismissRequest() {
        _categoryDialogVisible.value = false
    }

    fun onCategorySelected(value: TaskCategory) {
        _task.update { it.copy(category = value) }
    }

    fun onCategoryClick() {
        _categoryDialogVisible.value = true
    }
}