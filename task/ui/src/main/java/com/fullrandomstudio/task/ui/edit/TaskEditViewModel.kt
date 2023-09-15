package com.fullrandomstudio.task.ui.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandomstudio.core.ui.effect.EffectStateFlow
import com.fullrandomstudio.core.ui.effect.MutableEffectStateFlow
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskAlarm
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.task.ui.edit.effect.PopTaskEdit
import com.fullrandomstudio.task.ui.edit.effect.TaskEditScreenNavigation
import com.fullrandomstudio.task.ui.edit.effect.TaskPrepareErrorPop
import com.fullrandomstudio.todosimply.task.domain.GetAllCategoriesUseCase
import com.fullrandomstudio.todosimply.task.domain.PrepareTaskToEditUseCase
import com.fullrandomstudio.todosimply.task.domain.SaveTaskUseCase
import com.fullrandomstudio.todosimply.task.domain.SetTaskDoneUseCase
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

private const val KEY_SAVED_STATE_NAME = "name"
private const val KEY_SAVED_STATE_DESCRIPTION = "description"
private const val KEY_SAVED_STATE_DATE_TIME = "date_time"
private const val KEY_SAVED_STATE_ALARM = "alarm"
private const val KEY_SAVED_STATE_CATEGORY = "category"

@Suppress("TooManyFunctions")
@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val prepareTaskToEditUseCase: PrepareTaskToEditUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val setTaskDoneUseCase: SetTaskDoneUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args: TaskEditArgs = TaskEditArgs(savedStateHandle)

    val taskName: MutableState<String> = mutableStateOf(
        savedStateHandle[KEY_SAVED_STATE_NAME] ?: ""
    )
    val taskDescription: MutableState<String> = mutableStateOf(
        savedStateHandle[KEY_SAVED_STATE_DESCRIPTION] ?: ""
    )

    private val _task: MutableStateFlow<Task> = MutableStateFlow(Task.empty(args.scheduled))
    private val _categoryDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _categories: MutableStateFlow<List<TaskCategory>> = MutableStateFlow(emptyList())
    private val _datePickerDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _timePickerDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _validationErrors: MutableStateFlow<ImmutableSet<TaskEditValidationError>> =
        MutableStateFlow(
            emptyList<TaskEditValidationError>().toImmutableSet()
        )
    private val _navigationStateFlow = MutableEffectStateFlow<TaskEditScreenNavigation>()

    val task: StateFlow<Task> = _task
    val categoryPickerVisible: StateFlow<Boolean> = _categoryDialogVisible
    val taskCategories: StateFlow<List<TaskCategory>> = _categories
    val datePickerVisible: StateFlow<Boolean> = _datePickerDialogVisible
    val timePickerVisible: StateFlow<Boolean> = _timePickerDialogVisible
    val validationErrors: StateFlow<ImmutableSet<TaskEditValidationError>> = _validationErrors
    val navigationStateFlow: EffectStateFlow<TaskEditScreenNavigation> = _navigationStateFlow

    init {
        setupTask()
    }

    private fun setupTask() {
        viewModelScope.launch {
            _categories.value = getAllCategoriesUseCase()

            val prepareTaskResult: Result<Task> = prepareTaskToEditUseCase(
                taskEditType = args.taskEditType,
                taskId = args.taskId,
                scheduled = args.scheduled,
                selectedDate = args.selectedDate,
            )

            prepareTaskResult.fold(
                {
                    _task.value = mergeTaskWithSavedState(it)
                    updateTaskNameAndDescription(it)
                },
                {
                    _navigationStateFlow.emit(TaskPrepareErrorPop)
                }
            )
        }
    }

    private fun updateTaskNameAndDescription(task: Task) {
        if (!savedStateHandle.contains(KEY_SAVED_STATE_NAME)) {
            taskName.value = task.name
        }
        if (!savedStateHandle.contains(KEY_SAVED_STATE_DESCRIPTION)) {
            taskDescription.value = task.description
        }
    }

    private fun mergeTaskWithSavedState(task: Task): Task {
        val rememberedDateTime: ZonedDateTime? =
            savedStateHandle.get<String>(KEY_SAVED_STATE_DATE_TIME)
                ?.let { dateTimeString ->
                    ZonedDateTime.parse(dateTimeString)
                }
        val scheduleDateTime = rememberedDateTime ?: task.scheduleDateTime

        val rememberedAlarm: Boolean = savedStateHandle[KEY_SAVED_STATE_ALARM] ?: true
        val taskAlarm = if (rememberedAlarm) {
            createTaskAlarm(
                taskId = task.id,
                scheduleDateTime = requireNotNull(scheduleDateTime)
            )
        } else {
            null
        }

        val rememberedCategoryId: Long = savedStateHandle[KEY_SAVED_STATE_CATEGORY] ?: -1L
        val category = _categories.value.find { it.id == rememberedCategoryId } ?: task.category

        return task.copy(
            scheduleDateTime = scheduleDateTime,
            taskAlarm = taskAlarm,
            category = category
        )
    }

    fun onNameChange(value: String) {
        taskName.value = value
        savedStateHandle[KEY_SAVED_STATE_NAME] = value

        if (_validationErrors.value.contains(TaskEditValidationError.EMPTY_NAME)) {
            _validationErrors.value =
                _validationErrors.value.minus(TaskEditValidationError.EMPTY_NAME).toImmutableSet()
        }
    }

    fun onDescriptionChange(value: String) {
        taskDescription.value = value
        savedStateHandle[KEY_SAVED_STATE_DESCRIPTION] = value
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
            _navigationStateFlow.emit(PopTaskEdit)
        }
    }

    private fun validate(name: String): List<TaskEditValidationError> {
        if (name.isBlank()) {
            return listOf(TaskEditValidationError.EMPTY_NAME)
        }

        return emptyList()
    }

    fun onDoneClick() {
        val task = _task.value
        viewModelScope.launch {
            setTaskDoneUseCase(task.id, !task.isFinished)
            _navigationStateFlow.emit(PopTaskEdit)
        }
    }

    fun onAlarmChange() {
        _task.update { task ->
            val newAlarm: TaskAlarm? = if (task.hasAlarm) {
                null
            } else {
                createTaskAlarm(
                    taskId = task.id,
                    scheduleDateTime = requireNotNull(task.scheduleDateTime)
                )
            }

            savedStateHandle[KEY_SAVED_STATE_ALARM] = newAlarm != null

            task.copy(taskAlarm = newAlarm)
        }
    }

    private fun createTaskAlarm(
        taskId: Long,
        scheduleDateTime: ZonedDateTime
    ): TaskAlarm {
        return TaskAlarm(taskId, scheduleDateTime)
    }

    fun onCategoryPickerDismiss() {
        _categoryDialogVisible.value = false
    }

    fun onCategorySelected(value: TaskCategory) {
        _task.update {
            savedStateHandle[KEY_SAVED_STATE_CATEGORY] = value.id
            it.copy(category = value)
        }
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
            val currentScheduleDate = requireNotNull(it.scheduleDateTime?.toLocalTime())
            val newScheduleDate = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
            )
                .withHour(currentScheduleDate.hour)
                .withMinute(currentScheduleDate.minute)

            savedStateHandle[KEY_SAVED_STATE_DATE_TIME] = newScheduleDate.toString()

            it.copy(
                scheduleDateTime = newScheduleDate
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
            val newScheduleDate = requireNotNull(it.scheduleDateTime)
                .truncatedTo(ChronoUnit.DAYS)
                .withHour(time.hour)
                .withMinute(time.minute)

            savedStateHandle[KEY_SAVED_STATE_DATE_TIME] = newScheduleDate.toString()

            it.copy(
                scheduleDateTime = newScheduleDate
            )
        }
    }
}
