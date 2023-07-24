package com.fullrandomstudio.task.ui.scheduled

import androidx.lifecycle.ViewModel
import com.fullrandomstudio.task.model.DateRange
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduledTasksPagerViewModel @Inject constructor() : ViewModel() {

    val pageCount = Int.MAX_VALUE
    val initPage = Int.MAX_VALUE / 2
    private val initPageStartDate: LocalDate = LocalDate.now()
    private val timeRangeType = TimeRangeType.DAY // TODO later get from settings

    fun onAddTaskClick() {
        TODO("Not yet implemented")
    }

    fun dateRangeForPage(page: Int): DateRange {
        val pageDiff = (page - initPage).toLong()

        val startDate = when (timeRangeType) {
            TimeRangeType.DAY -> initPageStartDate.plusDays(pageDiff)
            TimeRangeType.WEEK -> initPageStartDate.plusDays(pageDiff)
        }
        val endDate = when (timeRangeType) {
            TimeRangeType.DAY -> startDate.plusDays(1)
            TimeRangeType.WEEK -> startDate.plusWeeks(1)
        }

        return DateRange(startDate, endDate)
    }
}
