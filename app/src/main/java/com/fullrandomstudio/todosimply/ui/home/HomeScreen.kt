@file:OptIn(ExperimentalMaterial3Api::class)

package com.fullrandomstudio.todosimply.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.todosimply.ui.home.bottombar.HomeBottomBar
import com.fullrandomstudio.todosimply.ui.home.bottombar.HomeNavBarDestination
import com.fullrandomstudio.todosimply.ui.home.navigation.HOME_SCHEDULED_TASKS_ROUTE
import com.fullrandomstudio.todosimply.ui.home.navigation.homeScheduledTasks
import com.fullrandomstudio.todosimply.ui.home.navigation.navigateToHomeScheduledTasks

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {

    val navController = rememberNavController()
    val currentDestination: NavDestination? = navController
        .currentBackStackEntryAsState().value?.destination

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        bottomBar = {
            HomeBottomBar(
                currentDestination = currentDestination,
                onNavigateToDestination = { destination ->
                    // TODO feature - when tapping selected destination reset
                    //  state to top of the list/default tab selection
                    navigateToDestination(navController, destination)
                }
            )
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = HOME_SCHEDULED_TASKS_ROUTE,
            modifier = Modifier.padding(paddingValues)
        ) {
            homeScheduledTasks()
        }
    }
}

private fun navigateToDestination(
    navController: NavHostController,
    destination: HomeNavBarDestination
) {

    val navOptions = navOptions {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    when (destination) {
        HomeNavBarDestination.SCHEDULED_TASKS -> navController.navigateToHomeScheduledTasks(
            navOptions
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    TodoSimplyTheme {
        HomeScreen()
    }
}
