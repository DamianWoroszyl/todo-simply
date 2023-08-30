package com.fullrandomstudio.todosimply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.todosimply.ui.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TodoSimplyTheme {
                AppNavigation()
            }
        }
    }
}
