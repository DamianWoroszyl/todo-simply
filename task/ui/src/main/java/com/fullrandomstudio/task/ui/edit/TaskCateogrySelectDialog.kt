@file:OptIn(ExperimentalMaterial3Api::class)

package com.fullrandomstudio.task.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.task.model.TaskCategory
import kotlinx.coroutines.launch

@Composable
fun TaskCategorySelectBottomSheet(
    categories: List<TaskCategory>,
    onCategoryClick: (TaskCategory) -> Unit,
    onDismissRequest: () -> Unit
) {
    val bottomSheetState = rememberSheetState(skipHalfExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
    ) {
        TaskCategorySelectDialogContent(
            categories = categories,
            onCategoryClick = {
                onCategoryClick(it)
                coroutineScope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        onDismissRequest()
                    }
                }
            },
        )
    }
}

@Composable
fun TaskCategorySelectDialogContent(
    categories: List<TaskCategory>,
    onCategoryClick: (TaskCategory) -> Unit,
) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(categories, key = { it.id }) {
            TaskCategoryItem(
                category = it,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@Composable
internal fun TaskCategoryItem(
    category: TaskCategory,
    onCategoryClick: (TaskCategory) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCategoryClick(category) }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = com.fullrandomstudio.todosimply.common.R.drawable.ic_category),
            contentDescription = null,
            tint = Color(category.color),
            modifier = Modifier
                .padding(top = 2.dp)
                .size(16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = category.name, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview
@Composable
fun TaskCategorySelectDialogPreview() {
    TodoSimplyTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            TaskCategorySelectDialogContent(
                categories = listOf(
                    TaskCategory(
                        name = "Default",
                        color = Color.Magenta.toArgb(),
                        isDefault = false,
                        id = 0
                    ),
                    TaskCategory(
                        name = "Home",
                        color = Color.DarkGray.toArgb(),
                        isDefault = false,
                        id = 1
                    )
                ),
                onCategoryClick = {},
            )
        }
    }
}
