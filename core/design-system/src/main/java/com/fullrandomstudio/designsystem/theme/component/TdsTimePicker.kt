@file:OptIn(ExperimentalMaterial3Api::class)

package com.fullrandomstudio.designsystem.theme.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.designsystem.theme.token.DialogTokens
import com.fullrandomstudio.designsystem.theme.token.toShape
import com.fullrandomstudio.todosimply.common.R
import java.time.LocalTime

@Composable
fun TdsTimePicker(
    initialTime: LocalTime,
    onDismiss: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
) {
    val timePickerState: TimePickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            tonalElevation = DialogTokens.TonalElevation,
            shape = DialogTokens.Shape.toShape()
        ) {
            Column {
                Box(
                    modifier = Modifier.padding(
                        top = 32.dp, start = 32.dp, end = 32.dp
                    )
                ) {
                    TimePicker(state = timePickerState)
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(DialogTokens.ButtonsPadding)
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }

                    Spacer(Modifier.width(DialogTokens.ButtonsMainAxisSpacing))

                    TextButton(
                        onClick = {
                            onTimeSelected(
                                LocalTime.of(
                                    timePickerState.hour, timePickerState.minute
                                )
                            )
                        }
                    ) {
                        Text(stringResource(id = R.string.ok))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TdsTimePickerPreview() {
    TodoSimplyTheme {
        TdsTimePicker(
            initialTime = LocalTime.of(14, 15),
            onDismiss = {},
            onTimeSelected = {}
        )
    }
}
