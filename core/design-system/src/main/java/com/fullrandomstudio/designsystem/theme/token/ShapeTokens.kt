package com.fullrandomstudio.designsystem.theme.token

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape

enum class ShapeTokens {
    ExtraLarge
}

internal fun Shapes.fromToken(value: ShapeTokens): Shape {
    return when (value) {
        ShapeTokens.ExtraLarge -> extraLarge
    }
}

/** Converts a color token key to the local color provided by the theme */
@Composable
fun ShapeTokens.toShape(): Shape {
    return MaterialTheme.shapes.fromToken(this)
}
