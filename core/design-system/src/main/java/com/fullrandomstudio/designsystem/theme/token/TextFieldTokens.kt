package com.fullrandomstudio.designsystem.theme.token

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

object TextFieldTokens {
    val HeaderFontSize = 22.sp
    val RegularFontSize = 16.sp
    val Background
        @Composable get() = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
}
