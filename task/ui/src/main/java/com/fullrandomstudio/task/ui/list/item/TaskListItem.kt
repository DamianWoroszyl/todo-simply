package com.fullrandomstudio.task.ui.list.item

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.fullrandomstudio.designsystem.theme.TodoSimplyTheme
import com.fullrandomstudio.task.ui.list.res.taskActionResource
import com.fullrandomstudio.task.ui.previewdata.previewDoneTaskUiState
import com.fullrandomstudio.task.ui.previewdata.previewTodoTaskUiState
import com.fullrandomstudio.todosimply.task.data.config.TaskAction
import com.fullrandomstudio.todosimply.task.ui.R
import com.fullrandomstudio.todosimply.util.formatTimeLocalized

@Composable
internal fun TaskListItem(
    state: TaskListItemUiState,
    onClick: (taskId: Long) -> Unit,
    onToggleAlarm: (taskId: Long) -> Unit,
    onTaskActionClick: (taskId: Long, taskAction: TaskAction) -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .clickable { onClick(state.task.id) }
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp)
        ) {

            val contentAlpha = if (state.task.isFinished) 0.5f else 1f
            val taskStatusImage =
                if (state.task.isFinished) R.drawable.ic_task_done else R.drawable.ic_task_not_done
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .alpha(contentAlpha)
            ) {
                val (doneCheckbox, title, time, alarm) = createRefs()

                Image(
                    imageVector = ImageVector.vectorResource(taskStatusImage),
                    colorFilter = ColorFilter.tint(Color(state.task.category.color)),
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                        .constrainAs(doneCheckbox) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                )

                val leftBarrier = createEndBarrier(doneCheckbox, margin = 4.dp)

                AlarmToggleButton(
                    isChecked = state.task.hasAlarm,
                    onClick = { onToggleAlarm(state.task.id) },
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp)
                        .constrainAs(alarm) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                )

                val textStyle = MaterialTheme.typography.titleMedium.let {
                    if (state.task.isFinished) {
                        it.copy(textDecoration = TextDecoration.LineThrough)
                    } else {
                        it
                    }
                }

                Text(
                    text = state.task.title,
                    style = textStyle,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            end.linkTo(alarm.start)
                            start.linkTo(leftBarrier)
                            width = Dimension.fillToConstraints
                        }
                        .padding(top = 2.dp, end = 8.dp)
                )

                if (state.task.isScheduled) {
                    Text(
                        text = formatTimeLocalized(requireNotNull(state.task.scheduleDate)),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.constrainAs(time) {
                            top.linkTo(title.bottom)
                            start.linkTo(leftBarrier)
                        }
                    )
                }

                Text(
                    text = state.task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .constrainAs(createRef()) {
                            top.linkTo(time.bottom)
                            start.linkTo(leftBarrier)
                        }
                )
            }

            val actionsVisible = state.expanded
            AnimatedVisibility(
                visible = actionsVisible,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.End)
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .alpha(2f)
                ) {
                    for (taskAction in state.taskActions) {
                        TaskActionButton(
                            taskAction = taskAction,
                            onClick = { onTaskActionClick(state.task.id, taskAction) }
                        )
                    }
                }
            }
        }

    }

}

//TODO use local providers for text? I have two custom colors -
// lighter and darker modifications of primary especially for task action icons
@Composable
internal fun TaskActionButton(
    taskAction: TaskAction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = taskActionResource(taskAction)

    IconButton(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(minWidth = 72.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = resources.drawableRes),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = resources.stringRes),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
internal fun AlarmToggleButton(
    isChecked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        checked = isChecked,
        onCheckedChange = { onClick() },
        modifier = modifier
    ) {
        val transition = updateTransition(isChecked, label = "Checked indicator")

        val tint = MaterialTheme.colorScheme.primary
        val alpha by transition.animateFloat(label = "Alpha") {
            if (it) 1f else 0.7f
        }

        val size by transition.animateDp(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    keyframes {
                        durationMillis = 250
                        32.dp at 0 with LinearOutSlowInEasing
                        40.dp at 150 with FastOutLinearInEasing
                    }
                } else {
                    keyframes {
                        durationMillis = 250
                        32.dp at 0 with LinearOutSlowInEasing
                        40.dp at 150 with FastOutLinearInEasing
                    }
                }
            },
            label = "Size"
        ) { 32.dp }

        val imageVectorRes = if (isChecked) {
            R.drawable.ic_alarm
        } else {
            R.drawable.ic_alarm_off
        }
        Icon(
            imageVector = ImageVector.vectorResource(id = imageVectorRes),
            contentDescription = null,
            tint = tint,
            modifier = Modifier
                .size(size)
                .alpha(alpha),
        )
    }
}

@Preview
@Composable
internal fun AlarmToggleButtonPreview() {
    TodoSimplyTheme {
        AlarmToggleButton(isChecked = true, onClick = { })
    }
}

@Preview
@Composable
private fun TaskListItemPreviewTodo() {
    val state = previewTodoTaskUiState()
    TodoSimplyTheme {
        TaskListItem(
            state = state,
            onClick = {}, onToggleAlarm = {}, onTaskActionClick = { _, _ -> }
        )
    }
}


@Preview
@Composable
private fun TaskListItemPreviewDone() {
    val state = previewDoneTaskUiState()
    TodoSimplyTheme {
        TaskListItem(
            state = state,
            onClick = {}, onToggleAlarm = {}, onTaskActionClick = { _, _ -> }
        )
    }
}
