package com.fullrandomstudio.todosimply.ui.home.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fullrandomstudio.todosimply.common.R
import com.fullrandomstudio.todosimply.ui.home.navigation.HomeTabScreen

enum class HomeNavBarDestination(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val textRes: Int,
) {
    SCHEDULED_TASKS(
        route = HomeTabScreen.ScheduledTasks.route,
        iconRes = R.drawable.ic_calendar,
        textRes = R.string.label_scheduled_tasks,
    ),
}
