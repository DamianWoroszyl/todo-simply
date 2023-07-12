package com.fullrandomstudio.task.ui.list.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

@Composable
internal fun MarkerSeparator(
    color: Int = MaterialTheme.colorScheme.secondary.toArgb()
) {
    Spacer(
        modifier = Modifier
            .width(36.dp)
            .height(2.dp)
            .background(Color(color))
    )
}
