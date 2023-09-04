package com.fullrandomstudio.designsystem.theme.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme

@Composable
fun TdsSeparatorHorizontal(
    thickness: Dp,
    modifier: Modifier = Modifier,
    color: Color = TodoSimplyTheme.colorScheme.separator
) {
    Surface(
        shape = CircleShape,
        color = color,
        modifier = modifier
            .height(thickness)
            .fillMaxWidth(),
        content = {}
    )
}

@Composable
fun TdsSeparator(
    modifier: Modifier = Modifier
) {
    Surface(
        shape = CircleShape,
        color = TodoSimplyTheme.colorScheme.separator,
        modifier = modifier,
        content = {}
    )
}
