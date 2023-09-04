package com.fullrandomstudio.designsystem.theme.token

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ColorKeyTokens {
    Primary,
    Secondary,
    Transparent,
    Background
}

internal fun ColorScheme.fromToken(value: ColorKeyTokens): Color {
    return when (value) {
        ColorKeyTokens.Primary -> primary
        ColorKeyTokens.Secondary -> secondary
        ColorKeyTokens.Transparent -> Color.Transparent
        ColorKeyTokens.Background -> background
    }
}

/** Converts a color token key to the local color provided by the theme */
@Composable
fun ColorKeyTokens.toColor(): Color {
    return MaterialTheme.colorScheme.fromToken(this)
}
