package com.fullrandomstudio.task.ui.scheduled

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.task.model.DateRange
import com.fullrandomstudio.task.ui.scheduled.ScheduledTasksListViewModel.ScheduledTasksListViewModelAssistedFactory
import com.fullrandomstudio.todosimply.task.ui.R
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

@Composable
fun ScheduledTasksPagerScreen(
    modifier: Modifier = Modifier,
    viewModel: ScheduledTasksPagerViewModel = hiltViewModel()
) {
    ScheduledTasksPagerScreen(
        onAddTaskClick = { viewModel.onAddTaskClick() },
        initialPage = viewModel.initPage,
        pageCount = viewModel.pageCount,
        pageScreenProvider = { page ->
            val dateRange: DateRange = viewModel.dateRangeForPage(page)
            ScheduledTasksListScreen(
                dateRange = dateRange,
                viewModel = viewModel(
                    key = dateRange.toString(),
                    factory = scheduledTasksListViewModelProviderFactory(dateRange)
                )
            )
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScheduledTasksPagerScreen(
    onAddTaskClick: () -> Unit,
    pageScreenProvider: @Composable (Int) -> Unit,
    pageCount: Int,
    initialPage: Int,
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

            val pagerState = rememberPagerState(initialPage = initialPage) {
                pageCount
            }

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
            pageCount = 1,
            initialPage = 1,
            onAddTaskClick = {},
            pageScreenProvider = {}
        )
    }
}
