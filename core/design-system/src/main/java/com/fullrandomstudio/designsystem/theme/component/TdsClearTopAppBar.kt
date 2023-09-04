@file:OptIn(ExperimentalMaterial3Api::class)

package com.fullrandomstudio.designsystem.theme.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.fullrandomstudio.designsystem.theme.token.AppBarTokens
import com.fullrandomstudio.designsystem.theme.token.IconButtonTokens
import com.fullrandomstudio.designsystem.theme.token.toColor

@Composable
fun TdsClearTopAppBar(
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    elevated: Boolean,
    modifier: Modifier = Modifier
) {
    val elevation by animateDpAsState(
        targetValue = if (elevated) 8.dp else 0.dp,
        label = "topBarElevation",
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    TopAppBar(
        title = {},
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppBarTokens.UserTaskScreenAppBarColor.toColor(),
            navigationIconContentColor = IconButtonTokens.ClearAppBarTint.toColor(),
            actionIconContentColor = IconButtonTokens.ClearAppBarTint.toColor()
        ),
        modifier = modifier
            .shadow(elevation)
    )
}
