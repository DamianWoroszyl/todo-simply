package com.fullrandomstudio.task.ui.scheduled

import androidx.lifecycle.ViewModel
import com.fullrandomstudio.core.ui.Navigator
import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.ui.common.EditTask
import com.fullrandomstudio.task.ui.edit.TaskEditArgs
import com.fullrandomstudio.todosimply.task.domain.TaskEditType
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduledTasksPagerViewModel @Inject constructor(
    val navigator: Navigator
) : ViewModel() {

    val pageCount = Int.MAX_VALUE
    val initPage = Int.MAX_VALUE / 2
    private val initPageStartDate: LocalDate = LocalDate.now()
    private val timeRangeType = TimeRangeType.DAY // TODO later get from settings

    fun onAddTaskClick(currentPage: Int) {
        navigator.navigate(
            EditTask(
                TaskEditArgs(
                    taskEditType = TaskEditType.CREATE,
                    scheduled = true,
                    selectedDate = dateRangeForPage(currentPage).start
                )
            )
        )
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
}
