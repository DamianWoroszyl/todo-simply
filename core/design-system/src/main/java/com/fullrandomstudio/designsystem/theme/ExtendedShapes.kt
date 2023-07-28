package com.fullrandomstudio.designsystem.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape

@Immutable
data class ExtendedShapes(
    val fab: Shape,
)

val LocalExtendedShapes: ProvidableCompositionLocal<ExtendedShapes> = staticCompositionLocalOf {
    ExtendedShapes(
        fab = CircleShape
    )
}
