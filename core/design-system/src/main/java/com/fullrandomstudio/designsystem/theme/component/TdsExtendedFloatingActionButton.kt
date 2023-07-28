package com.fullrandomstudio.designsystem.theme.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.designsystem.theme.token.FabTokens

private val ExtendedFabVerticalPadding = 12.dp
private val ExtendedFabHorizontalPadding = 24.dp
private val ExtendedFabTextIconPadding = 8.dp
private val DefaultMinSize = 48.dp

@Composable
fun TdsExtendedFloatingActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.tertiary,
        shape = TodoSimplyTheme.shapes.fab,
        modifier = modifier
            .defaultMinSize(minWidth = DefaultMinSize, minHeight = DefaultMinSize)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = ExtendedFabHorizontalPadding,
                    vertical = ExtendedFabVerticalPadding
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text.uppercase())
            Spacer(modifier = Modifier.width(ExtendedFabTextIconPadding))
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(FabTokens.IconSize)
            )
        }
    }
}

@Composable
fun TdsExtendedFloatingActionButton(
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.tertiary,
        shape = TodoSimplyTheme.shapes.fab,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = ExtendedFabHorizontalPadding)
        ) {
            text()
            Spacer(modifier = Modifier.width(ExtendedFabTextIconPadding))
            icon()
        }
    }
}

@Preview
@Composable
private fun TdsExtendedFloatingActionButtonPreview() {
    TodoSimplyTheme {
        TdsExtendedFloatingActionButton(
            text = "Click",
            icon = Icons.Filled.Build,
            onClick = { }
        )
    }
}
