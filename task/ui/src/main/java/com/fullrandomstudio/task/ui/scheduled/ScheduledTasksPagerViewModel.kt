package com.fullrandomstudio.task.ui.scheduled

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandomstudio.core.ui.effect.EffectStateFlow
import com.fullrandomstudio.core.ui.effect.MutableEffectStateFlow
import com.fullrandomstudio.core.ui.navigation.NavigationStateFlow
import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.ui.common.EditTask
import com.fullrandomstudio.task.ui.edit.TaskEditArgs
import com.fullrandomstudio.task.ui.scheduled.effect.DeleteTaskEffect
import com.fullrandomstudio.task.ui.scheduled.effect.ScheduledTasksPagerScreenEffect
import com.fullrandomstudio.todosimply.task.domain.TaskEditType
import com.fullrandomstudio.todosimply.task.domain.UndoTaskDeleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduledTasksPagerViewModel @Inject constructor(
    private val undoTaskDeleteUseCase: UndoTaskDeleteUseCase,
    val navigationStateFlow: NavigationStateFlow,
) : ViewModel() {

    val pageCount = Int.MAX_VALUE
    val initPage = Int.MAX_VALUE / 2
    private val initPageStartDate: LocalDate = LocalDate.now()
    private val timeRangeType = TimeRangeType.DAY // TODO later get from settings

    private val _effectStateFlow = MutableEffectStateFlow<ScheduledTasksPagerScreenEffect>()
    val effectStateFlow: EffectStateFlow<ScheduledTasksPagerScreenEffect> = _effectStateFlow

    fun onAddTaskClick(currentPage: Int) {
        navigationStateFlow.navigate(
            EditTask(
                TaskEditArgs(
                    taskEditType = TaskEditType.CREATE,
                    scheduled = true,
                    selectedDate = dateRangeForPage(currentPage).start
                )
            )
        )
    }

    fun onTaskDeleteUndoClick(taskId: Long) {
        viewModelScope.launch {
            undoTaskDeleteUseCase(taskId)
        }
    }

    fun dateRangeForPage(page: Int): DateRange {
        val pageDiff = (page - initPage).toLong()

        val startDate = when (timeRangeType) {
            TimeRangeType.DAY -> initPageStartDate.plusDays(pageDiff)
            TimeRangeType.WEEK -> initPageStartDate.plusDays(pageDiff)
        }
        val endDate = when (timeRangeType) {
            TimeRangeType.DAY -> startDate
            TimeRangeType.WEEK -> startDate.plusWeeks(1).minusDays(1)
        }

        return DateRange(startDate, endDate)
    }

    fun onShowDeleteSnackbar(taskId: Long, taskName: String) {
        _effectStateFlow.emit(DeleteTaskEffect(taskId, taskName))
    }
}
