@file:OptIn(ExperimentalFoundationApi::class)

package com.fullrandomstudio.task.ui.scheduled

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fullrandomstudio.core.ui.CollectNavigation
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.ui.common.EditTask
import com.fullrandomstudio.task.ui.edit.TaskEditArgs
import com.fullrandomstudio.task.ui.scheduled.ScheduledTasksListViewModel.ScheduledTasksListViewModelAssistedFactory
import com.fullrandomstudio.todosimply.task.ui.R
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

@Composable
fun ScheduledTasksPagerScreen(
    onEditTask: (taskEditArgs: TaskEditArgs) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScheduledTasksPagerViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(initialPage = viewModel.initPage) {
        viewModel.pageCount
    }

    ScheduledTasksPagerScreen(
        pagerState = pagerState,
        onAddTaskClick = { viewModel.onAddTaskClick(pagerState.currentPage) },
        pageScreenProvider = { page ->
            val dateRange: DateRange = viewModel.dateRangeForPage(page)
            ScheduledTasksListScreen(
                onEditTask = onEditTask,
                viewModel = viewModel(
                    key = dateRange.toString(),
                    factory = scheduledTasksListViewModelProviderFactory(dateRange)
                )
            )
        },
        modifier = modifier
    )

    CollectNavigation(
        navigator = viewModel.navigator,
        autoCollect = true,
    ) { _, command ->
        when (command) {
            is EditTask -> onEditTask(command.args)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduledTasksPagerScreen(
    pagerState: PagerState,
    onAddTaskClick: () -> Unit,
    pageScreenProvider: @Composable (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                shape = CircleShape,
                onClick = { onAddTaskClick() }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HorizontalPager(
                state = pagerState,
                beyondBoundsPageCount = 2,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                pageScreenProvider(page)
            }
        }
    }
}

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun scheduledTasksListViewModelFactory(): ScheduledTasksListViewModelAssistedFactory
}

@Composable
private fun scheduledTasksListViewModelProviderFactory(
    dateRange: DateRange
): ViewModelProvider.Factory {
    val activity: Activity = LocalContext.current as Activity
    val viewModelAssistedFactory: ScheduledTasksListViewModelAssistedFactory =
        EntryPointAccessors.fromActivity(
            activity,
            ViewModelFactoryProvider::class.java
        ).scheduledTasksListViewModelFactory()

    return ScheduledTasksListViewModel.provideViewModelFactory(
        viewModelAssistedFactory, dateRange
    )
}

@Preview
@Composable
internal fun ScheduledTasksPagerScreenPreview() {
    TodoSimplyTheme {
        ScheduledTasksPagerScreen(
            pagerState = rememberPagerState { 0 },
            onAddTaskClick = {},
            pageScreenProvider = {}
        )
    }
}
