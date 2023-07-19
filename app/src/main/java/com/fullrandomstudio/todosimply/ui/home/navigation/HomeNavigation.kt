package com.fullrandomstudio.todosimply.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fullrandomstudio.todosimply.ui.home.HomeScreen

const val HOME_NAV_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_NAV_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScreen(mainNavController: NavController) {
    composable(
        route = HOME_NAV_ROUTE
    ) {
        HomeScreen()
    }
}
