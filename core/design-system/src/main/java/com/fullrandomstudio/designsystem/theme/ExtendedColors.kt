package com.fullrandomstudio.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColors(
    val separator: Color,
)

val LocalExtendedColors: ProvidableCompositionLocal<ExtendedColors> = staticCompositionLocalOf {
    ExtendedColors(
        separator = Color.Unspecified
    )
}
