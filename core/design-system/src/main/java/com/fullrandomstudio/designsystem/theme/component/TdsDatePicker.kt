@file:OptIn(ExperimentalMaterial3Api::class)

package com.fullrandomstudio.designsystem.theme.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.designsystem.theme.token.DialogTokens
import com.fullrandomstudio.todosimply.common.R
import java.time.ZonedDateTime

private val yearRangeDefault = 2020..2040

@Composable
fun TdsDatePicker(
    initialDateTimestamp: Long,
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit,
    yearRange: IntRange = yearRangeDefault
) {
    val datePickerState: DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateTimestamp,
        initialDisplayedMonthMillis = initialDateTimestamp,
        yearRange = yearRange,
        initialDisplayMode = DisplayMode.Picker
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        tonalElevation = DialogTokens.TonalElevation,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        },
        confirmButton = {
            val confirmEnabled by remember {
                derivedStateOf { datePickerState.selectedDateMillis != null }
            }
            TextButton(
                onClick = {
                    onDateSelected(requireNotNull(datePickerState.selectedDateMillis))
                },
                enabled = confirmEnabled
            ) {
                Text(stringResource(id = R.string.ok))
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}

@Preview
@Composable
private fun TdsDatePickerPreview() {
    TodoSimplyTheme {
        TdsDatePicker(
            initialDateTimestamp = ZonedDateTime.now().toInstant().toEpochMilli(),
            onDismiss = {},
            onDateSelected = {},
        )
    }
}
