package com.fullrandomstudio.task.ui.list.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.todosimply.util.formatDayNameAndDateLocalized
import java.time.ZonedDateTime

@Composable
internal fun TasksDateMarkerListItem(
    state: TasksDateMarkerListItemUiState,
    modifier: Modifier = Modifier
) {

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            MarkerSeparator()
            Text(
                text = formatDayNameAndDateLocalized(state.date),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
            )
            MarkerSeparator()
        }
    }
}

@Preview
@Composable
internal fun TasksDateMarkerListItemPreview() {
    val state = TasksDateMarkerListItemUiState(ZonedDateTime.now())
    TodoSimplyTheme {
        TasksDateMarkerListItem(state)
    }
}
