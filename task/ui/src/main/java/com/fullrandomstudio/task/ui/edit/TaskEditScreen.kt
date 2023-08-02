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
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.designsystem.theme.component.TdsAppBarIconButton
import com.fullrandomstudio.designsystem.theme.component.TdsClearTopAppBar
import com.fullrandomstudio.designsystem.theme.component.TdsExtendedFloatingActionButton
import com.fullrandomstudio.designsystem.theme.component.TdsSeparatorHorizontal
import com.fullrandomstudio.designsystem.theme.component.TdsTextField
import com.fullrandomstudio.designsystem.theme.token.ScreenTokens
import com.fullrandomstudio.designsystem.theme.token.SeparatorTokens
import com.fullrandomstudio.designsystem.theme.token.TextFieldTokens
import com.fullrandomstudio.task.model.Task
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.task.ui.R
import com.fullrandomstudio.todosimply.util.formatDateLocalized
import com.fullrandomstudio.todosimply.util.formatTimeLocalized
import com.fullrandomstudio.todosimply.common.R as CommonR

@Composable
fun TaskEditScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskEditViewModel = hiltViewModel()
) {
    val task by viewModel.task.collectAsStateWithLifecycle()
    val taskCategories by viewModel.taskCategories.collectAsStateWithLifecycle()
    val categoryDialogVisible by viewModel.categoryDialogVisible.collectAsStateWithLifecycle()

    TaskEditScreen(
        task = task,
        name = viewModel.taskName.value,
        description = viewModel.taskDescription.value,
        onSaveClick = { viewModel.onSaveClick() },
        onBackClick = { TODO() },
        onDoneClick = { viewModel.onDoneClick() },
        onNameChange = { viewModel.onNameChange(it) },
        onDescriptionChange = { viewModel.onDescriptionChange(it) },
        onAlarmChange = { viewModel.onAlarmChange() },
        onCategoryClick = { viewModel.onCategoryClick() },
        categoryDialogVisible = categoryDialogVisible,
        taskCategories = taskCategories,
        onCategorySelected = { viewModel.onCategorySelected(it) },
        onCategorySelectDismissRequest = { viewModel.onCategorySelectDismissRequest() },
        modifier = modifier,
    )
}

@Composable
fun TaskEditScreen(
    task: Task,
    name: String,
    description: String,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAlarmChange: () -> Unit,
    onCategoryClick: () -> Unit,
    categoryDialogVisible: Boolean,
    taskCategories: List<TaskCategory>,
    onCategorySelected: (TaskCategory) -> Unit,
    onCategorySelectDismissRequest: () -> Unit,
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
        if (categoryDialogVisible) {
            TaskCategorySelectBottomSheet(
                categories = taskCategories,
                onCategoryClick = onCategorySelected,
                onDismissRequest = onCategorySelectDismissRequest
            )
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .fillMaxSize()
        ) {
            InputsContainer(
                task = task,
                name = name,
                description = description,
                onNameChange = onNameChange,
                onDescriptionChange = onDescriptionChange,
                onAlarmChange = onAlarmChange,
                onCategoryClick = onCategoryClick,
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
    task: Task,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAlarmChange: () -> Unit,
    onCategoryClick: () -> Unit,
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

        EditAttributeWithText(
            label = stringResource(id = CommonR.string.task_label_category),
            body = task.category.name,
            iconRes = CommonR.drawable.ic_category,
            iconTint = Color(task.category.color),
            onClick = onCategoryClick,
        )

        if (task.scheduled) {
            TdsSeparatorHorizontal(
                thickness = SeparatorTokens.ThicknessThin,
                modifier = Modifier.padding(start = EditAttributeTokens.separatorPadding)
            )

            EditAttributeWithText(
                label = stringResource(id = CommonR.string.task_label_date),
                body = formatDateLocalized(requireNotNull(task.scheduleDate)),
                iconRes = CommonR.drawable.ic_calendar,
                onClick = { TODO("Show date dialog") },
            )

            TdsSeparatorHorizontal(
                thickness = SeparatorTokens.ThicknessThin,
                modifier = Modifier.padding(start = EditAttributeTokens.separatorPadding)
            )

            EditAttributeWithText(
                label = stringResource(id = CommonR.string.task_label_time),
                body = formatTimeLocalized(requireNotNull(task.scheduleDate)),
                iconRes = CommonR.drawable.ic_schedule,
                onClick = { TODO("Show time dialog") },
            )

            TdsSeparatorHorizontal(
                thickness = SeparatorTokens.ThicknessThin,
                modifier = Modifier.padding(start = EditAttributeTokens.separatorPadding)
            )

            EditAttributeWithSwitch(
                label = stringResource(id = CommonR.string.task_label_alarm),
                body = task.hasAlarm,
                iconRes = CommonR.drawable.ic_alarm,
                onClick = onAlarmChange,
            )
        }
    }
}

@Preview
@Composable
private fun TaskEditScreenPreview() {
    TodoSimplyTheme {
        TaskEditScreen(
            task = Task.empty(true).copy(
                category = TaskCategory(
                    name = "Default",
                    color = Color.Magenta.toArgb(),
                    isDefault = false,
                    id = 0,
                )
            ),
            name = "",
            description = "",
            categoryDialogVisible = false,
            taskCategories = emptyList(),
            onSaveClick = {},
            onBackClick = {},
            onDoneClick = {},
            onNameChange = {},
            onDescriptionChange = {},
            onAlarmChange = {},
            onCategorySelected = {},
            onCategorySelectDismissRequest = {},
            onCategoryClick = {}
        )
    }
}
