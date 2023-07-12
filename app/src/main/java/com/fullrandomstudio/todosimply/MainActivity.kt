package com.fullrandomstudio.todosimply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.task.ui.scheduled.ScheduledTasksPagerScreen
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val x = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
        x.toString()
        setContent {
            TodoSimplyTheme {
                ScheduledTasksPagerScreen()
            }
        }
    }
}
