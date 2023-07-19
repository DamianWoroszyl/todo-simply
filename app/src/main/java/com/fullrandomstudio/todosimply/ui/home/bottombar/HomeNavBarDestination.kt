package com.fullrandomstudio.todosimply.ui.home.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fullrandomstudio.todosimply.common.R
import com.fullrandomstudio.todosimply.ui.home.navigation.HOME_SCHEDULED_TASKS_ROUTE

enum class HomeNavBarDestination(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val textRes: Int,
) {
    SCHEDULED_TASKS(
        route = HOME_SCHEDULED_TASKS_ROUTE,
        iconRes = R.drawable.ic_calendar,
        textRes = R.string.label_scheduled_tasks,
    ),
}
