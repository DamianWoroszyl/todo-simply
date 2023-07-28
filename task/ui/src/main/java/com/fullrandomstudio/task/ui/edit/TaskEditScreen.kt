@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.fullrandomstudio.task.ui.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.designsystem.theme.component.TdsAppBarIconButton
import com.fullrandomstudio.designsystem.theme.component.TdsClearTopAppBar
import com.fullrandomstudio.designsystem.theme.component.TdsExtendedFloatingActionButton
import com.fullrandomstudio.designsystem.theme.component.TdsSeparatorHorizontal
import com.fullrandomstudio.designsystem.theme.component.TdsTextField
import com.fullrandomstudio.designsystem.theme.token.ScreenTokens
import com.fullrandomstudio.designsystem.theme.token.SeparatorTokens
import com.fullrandomstudio.designsystem.theme.token.TextFieldTokens
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.task.ui.edit.EditAttribute.EditAttributeAlarm
import com.fullrandomstudio.task.ui.edit.EditAttribute.EditAttributeCategory
import com.fullrandomstudio.task.ui.edit.EditAttribute.EditAttributeDate
import com.fullrandomstudio.task.ui.edit.EditAttribute.EditAttributeTime
import com.fullrandomstudio.todosimply.task.ui.R
import java.time.LocalDate
import java.time.LocalTime
import com.fullrandomstudio.todosimply.common.R as CommonR

@Composable
fun TaskEditScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskEditViewModel = hiltViewModel()
) {
    TaskEditScreen(
        onSaveClick = {},
        onBackClick = {},
        onDoneClick = {},
        name = viewModel.taskName.value,
        description = viewModel.taskDescription.value,
        onNameChange = {
            viewModel.onNameChange(it)
        },
        onDescriptionChange = {
            viewModel.onDescriptionChange(it)
        },
        modifier = modifier,
    )
}

@Composable
fun TaskEditScreen(
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    name: String,
    description: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            TdsExtendedFloatingActionButton(
                text = stringResource(id = R.string.save_task),
                icon = ImageVector.vectorResource(id = R.drawable.ic_send),
                onClick = onSaveClick
            )
        },
        topBar = {
            ActionsContainer(
                onBackClick = onBackClick,
                onDoneClick = onDoneClick,
                elevated = scrollState.value > 0
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .fillMaxSize()
        ) {
            InputsContainer(
                name = name,
                description = description,
                onNameChange = onNameChange,
                onDescriptionChange = onDescriptionChange,
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(ScreenTokens.ScreenPadding)
                    .padding(bottom = 64.dp)
            )
        }
    }
}

@Composable
private fun ActionsContainer(
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    elevated: Boolean
) {
    TdsClearTopAppBar(
        navigationIcon = {
            TdsAppBarIconButton(
                onClick = onBackClick,
                iconRes = CommonR.drawable.ic_arrow_back,
            )
        },
        actions = {
            TdsAppBarIconButton(
                onClick = onDoneClick,
                iconRes = CommonR.drawable.ic_done
            )
        },
        elevated = elevated
    )
}

@Composable
private fun InputsContainer(
    name: String,
    description: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

        TdsTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(text = stringResource(id = CommonR.string.task_label_name)) },
            textStyle = LocalTextStyle.current.copy(fontSize = TextFieldTokens.HeaderFontSize),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        TdsTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(text = stringResource(id = CommonR.string.task_label_description)) },
            textStyle = LocalTextStyle.current.copy(fontSize = TextFieldTokens.RegularFontSize),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        TdsSeparatorHorizontal(thickness = SeparatorTokens.ThicknessBold)

        // todo dw this will come from viewmodel
        val taskAttributes: List<EditAttributeWithListener> = listOf(
            EditAttributeWithListener(
                editAttribute = EditAttributeCategory(
                    label = stringResource(id = CommonR.string.task_label_category),
                    taskCategory = TaskCategory("Home", Color.Green.toArgb(), false)
                ),
                onClick = {}
            ),
            EditAttributeWithListener(
                editAttribute = EditAttributeDate(
                    label = stringResource(id = CommonR.string.task_label_date),
                    date = LocalDate.now()
                ),
                onClick = {}
            ),
            EditAttributeWithListener(
                editAttribute = EditAttributeTime(
                    label = stringResource(id = CommonR.string.task_label_time),
                    time = LocalTime.now()
                ),
                onClick = {}
            ),
            EditAttributeWithListener(
                editAttribute = EditAttributeAlarm(
                    label = stringResource(id = CommonR.string.task_label_alarm),
                    alarmSet = true
                ),
                onClick = {}
            )
        )

        EditAttributesList(
            editAttributeWithListeners = taskAttributes,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun TaskEditScreenPreview() {
    TodoSimplyTheme {
        TaskEditScreen(
            onSaveClick = {},
            onBackClick = {},
            onDoneClick = {},
            name = "",
            description = "",
            onNameChange = {},
            onDescriptionChange = {},
        )
    }
}
