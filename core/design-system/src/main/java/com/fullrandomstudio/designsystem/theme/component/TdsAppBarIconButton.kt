package com.fullrandomstudio.designsystem.theme.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun TdsAppBarIconButton(
    onClick: () -> Unit,
    @DrawableRes iconRes: Int
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.padding(8.dp)
        )
    }
}
