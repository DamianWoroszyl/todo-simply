package com.fullrandomstudio.designsystem.theme.token

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.fullrandomstudio.designsystem.theme.util.pxToDp

object SeparatorTokens {
    val ThicknessBold = 2.dp
    val ThicknessThin
        @Composable get() = 1.pxToDp()
}
