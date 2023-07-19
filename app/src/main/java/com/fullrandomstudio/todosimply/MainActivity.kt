package com.fullrandomstudio.todosimply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.todosimply.ui.home.navigation.HOME_NAV_ROUTE
import com.fullrandomstudio.todosimply.ui.home.navigation.homeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TodoSimplyTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = HOME_NAV_ROUTE,
                    modifier = Modifier.fillMaxSize()
                ) {
                    homeScreen(navController)
                }
            }
        }
    }
}
