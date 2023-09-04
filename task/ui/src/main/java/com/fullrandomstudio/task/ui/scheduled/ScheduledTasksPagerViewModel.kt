package com.fullrandomstudio.task.ui.scheduled

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fullrandomstudio.core.ui.effect.EffectStateFlow
import com.fullrandomstudio.core.ui.navigation.NavigationStateFlow
import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.ui.common.EditTask
import com.fullrandomstudio.task.ui.edit.TaskEditArgs
import com.fullrandomstudio.task.ui.scheduled.effect.DeleteTaskEffect
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
    val effectStateFlow: EffectStateFlow,
) : ViewModel() {

    val pageCount = Int.MAX_VALUE
    val initPage = Int.MAX_VALUE / 2
    private val initPageStartDate: LocalDate = LocalDate.now()
    private val timeRangeType = TimeRangeType.DAY // TODO later get from settings

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

    fun onShowDeleteSnackbar(state: DeleteTaskEffect) {
        effectStateFlow.emit(state)
    }
}
