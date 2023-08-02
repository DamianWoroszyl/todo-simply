package com.fullrandomstudio.task.ui.edit

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.todosimply.common.R

@Composable
private fun EditAttributeLabel(
    label: String
) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
    )
}

@Composable
fun EditAttributeWithText(
    label: String,
    body: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.primary
) {
    EditAttributeContainer(
        body = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                EditAttributeLabel(label = label)
                Text(
                    text = body,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        iconRes = iconRes,
        iconTint = iconTint,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun EditAttributeWithSwitch(
    label: String,
    body: Boolean,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.primary
) {
    EditAttributeContainer(
        body = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                EditAttributeLabel(label = label)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = body,
                    onCheckedChange = null,
                    modifier = Modifier.scale(0.6f)
                )
            }
        },
        iconRes = iconRes,
        iconTint = iconTint,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
private fun EditAttributeContainer(
    body: @Composable () -> Unit,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        body()
    }
}

@Preview
@Composable
fun EditAttributesPreview() {
    TodoSimplyTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            EditAttributeWithText(
                label = "Category",
                body = "Home",
                iconRes = R.drawable.ic_category,
                iconTint = Color.Magenta,
                onClick = {},
            )

            EditAttributeWithSwitch(
                label = "Alarm",
                body = true,
                iconRes = R.drawable.ic_alarm,
                onClick = {},
            )
        }
    }
}

object EditAttributeTokens {
    val separatorPadding = 32.dp
}
