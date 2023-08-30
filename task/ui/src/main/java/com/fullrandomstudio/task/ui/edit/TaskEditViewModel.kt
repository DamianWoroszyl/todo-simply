package com.fullrandomstudio.task.ui.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandomstudio.core.ui.Navigator
import com.fullrandomstudio.core.ui.PopBackstack
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskAlarm
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.task.domain.GetAllCategoriesUseCase
import com.fullrandomstudio.todosimply.task.domain.PrepareTaskToEditUseCase
import com.fullrandomstudio.todosimply.task.domain.SaveTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@Suppress("TooManyFunctions")
@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val prepareTaskToEditUseCase: PrepareTaskToEditUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    val navigator: Navigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args: TaskEditArgs = TaskEditArgs(savedStateHandle)

    val taskName: MutableState<String> = mutableStateOf("") // todo persist across process death?
    val taskDescription: MutableState<String> = mutableStateOf("")

    private val _task: MutableStateFlow<Task> = MutableStateFlow(Task.empty(args.scheduled))
    private val _categoryDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _categories: MutableStateFlow<List<TaskCategory>> = MutableStateFlow(emptyList())
    private val _datePickerDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _timePickerDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _validationErrors: MutableStateFlow<ImmutableSet<TaskEditValidationError>> =
        MutableStateFlow(
            emptyList<TaskEditValidationError>().toImmutableSet()
        )

    val task: StateFlow<Task> = _task
    val categoryPickerVisible: StateFlow<Boolean> = _categoryDialogVisible
    val taskCategories: StateFlow<List<TaskCategory>> = _categories
    val datePickerVisible: StateFlow<Boolean> = _datePickerDialogVisible
    val timePickerVisible: StateFlow<Boolean> = _timePickerDialogVisible
    val validationErrors: StateFlow<ImmutableSet<TaskEditValidationError>> = _validationErrors

    init {
        setupCategories()
        setupTask()
    }

    private fun setupCategories() {
        viewModelScope.launch {
            _categories.value = getAllCategoriesUseCase()
        }
    }

    private fun setupTask() {
        viewModelScope.launch {
            val taskResult: Result<Task> = prepareTaskToEditUseCase(
                taskEditType = args.taskEditType,
                taskId = args.taskId,
                scheduled = args.scheduled,
                selectedDate = args.selectedDate,
            )

            taskResult.fold(
                {
                    _task.value = it
                    taskName.value = it.name
                    taskDescription.value = it.description
                },
                {
                    // todo show error and go back in later task
                }
            )
        }
    }

    fun onNameChange(value: String) {
        taskName.value = value

        if (_validationErrors.value.contains(TaskEditValidationError.EMPTY_NAME)) {
            _validationErrors.value =
                _validationErrors.value.minus(TaskEditValidationError.EMPTY_NAME).toImmutableSet()
        }
    }

    fun onDescriptionChange(value: String) {
        taskDescription.value = value
    }

    fun onSaveClick() {
        val name = taskName.value
        val validationResult = validate(name)
        if (validationResult.isNotEmpty()) {
            _validationErrors.value = validationResult.toImmutableSet()
            return
        }

        val task = task.value.copy(
            name = name,
            description = taskDescription.value
        )

        viewModelScope.launch {
            saveTaskUseCase(task)
            navigator.navigate(PopBackstack)
        }
    }

    private fun validate(name: String): List<TaskEditValidationError> {
        if (name.isBlank()) {
            return listOf(TaskEditValidationError.EMPTY_NAME)
        }

        return emptyList()
    }

    fun onDoneClick() {
        TODO("Not yet implemented")
    }

    fun onAlarmChange() {
        _task.update { task ->
            val newAlarm: TaskAlarm? = if (task.hasAlarm) {
                null
            } else {
                TaskAlarm(0, task.id, requireNotNull(task.scheduleDateTime))
            }

            task.copy(taskAlarm = newAlarm)
        }
    }

    fun onCategoryPickerDismiss() {
        _categoryDialogVisible.value = false
    }

    fun onCategorySelected(value: TaskCategory) {
        _task.update { it.copy(category = value) }
    }

    fun onCategoryClick() {
        _categoryDialogVisible.value = true
    }

    fun onScheduleDateClick() {
        _datePickerDialogVisible.value = true
    }

    fun onDatePickerDismiss() {
        _datePickerDialogVisible.value = false
    }

    fun onDateSelected(timestamp: Long) {
        _datePickerDialogVisible.value = false
        _task.update {
            val time = requireNotNull(it.scheduleDateTime?.toLocalTime())
            it.copy(
                scheduleDateTime = ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(timestamp),
                    ZoneId.systemDefault()
                )
                    .withHour(time.hour)
                    .withMinute(time.minute)
            )
        }
    }

    fun onScheduleTimeClick() {
        _timePickerDialogVisible.value = true
    }

    fun onTimePickerDismiss() {
        _timePickerDialogVisible.value = false
    }

    fun onTimeSelected(time: LocalTime) {
        _timePickerDialogVisible.value = false
        _task.update {
            it.copy(
                scheduleDateTime = requireNotNull(it.scheduleDateTime)
                    .truncatedTo(ChronoUnit.DAYS)
                    .withHour(time.hour)
                    .withMinute(time.minute)
            )
        }
    }
}
