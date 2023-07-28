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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.designsystem.theme.component.TdsSeparatorHorizontal
import com.fullrandomstudio.designsystem.theme.token.SeparatorTokens
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.common.R
import com.fullrandomstudio.todosimply.util.formatDateLocalized
import com.fullrandomstudio.todosimply.util.formatTimeLocalized
import java.time.LocalDate
import java.time.LocalTime

sealed class EditAttribute {
    abstract val label: String

    data class EditAttributeCategory(
        override val label: String,
        val taskCategory: TaskCategory,
    ) : EditAttribute()

    data class EditAttributeDate(
        override val label: String,
        val date: LocalDate,
    ) : EditAttribute()

    data class EditAttributeTime(
        override val label: String,
        val time: LocalTime,
    ) : EditAttribute()

    data class EditAttributeAlarm(
        override val label: String,
        val alarmSet: Boolean,
    ) : EditAttribute()
}

data class EditAttributeWithListener(
    val editAttribute: EditAttribute,
    val onClick: () -> Unit
)

@Composable
fun EditAttributesList(
    editAttributeWithListeners: List<EditAttributeWithListener>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        editAttributeWithListeners.forEachIndexed { index, attributeWithListener ->
            val (attribute, onClick) = attributeWithListener
            when (attribute) {
                is EditAttribute.EditAttributeCategory -> EditAttributeWithText(
                    label = attribute.label,
                    body = attribute.taskCategory.name,
                    iconRes = R.drawable.ic_category,
                    iconTint = Color(attribute.taskCategory.color),
                    onClick = onClick,
                )

                is EditAttribute.EditAttributeDate -> EditAttributeWithText(
                    label = attribute.label,
                    body = formatDateLocalized(attribute.date),
                    iconRes = R.drawable.ic_calendar,
                    onClick = onClick,
                )

                is EditAttribute.EditAttributeTime -> EditAttributeWithText(
                    label = attribute.label,
                    body = formatTimeLocalized(attribute.time),
                    iconRes = R.drawable.ic_schedule,
                    onClick = onClick,
                )

                is EditAttribute.EditAttributeAlarm -> EditAttributeWithSwitch(
                    label = attribute.label,
                    body = attribute.alarmSet,
                    iconRes = R.drawable.ic_alarm,
                    onClick = onClick,
                )
            }

            if (index != editAttributeWithListeners.lastIndex) {
                TdsSeparatorHorizontal(
                    thickness = SeparatorTokens.ThicknessThin,
                    modifier = Modifier.padding(start = 32.dp)
                )
            }
        }
    }
}

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
    val taskAttributes: List<EditAttribute> = listOf(
        EditAttribute.EditAttributeCategory(
            label = stringResource(id = R.string.task_label_category),
            taskCategory = TaskCategory("Home", Color.Green.toArgb(), false)
        ),
        EditAttribute.EditAttributeDate(
            label = stringResource(id = R.string.task_label_date),
            date = LocalDate.now()
        ),
        EditAttribute.EditAttributeTime(
            label = stringResource(id = R.string.task_label_time),
            time = LocalTime.now()
        ),
        EditAttribute.EditAttributeAlarm(
            label = stringResource(id = R.string.task_label_alarm),
            alarmSet = true
        )
    )

    TodoSimplyTheme {
        EditAttributesList(
            editAttributeWithListeners = taskAttributes.map {
                EditAttributeWithListener(it, {})
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}
