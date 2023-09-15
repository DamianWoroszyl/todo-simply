@file:Suppress("Filename", "MatchingDeclarationName")

package com.fullrandomstudio.task.ui.common

import com.fullrandomstudio.core.ui.navigation.NavigationCommand
import com.fullrandomstudio.task.ui.edit.TaskEditArgs

data class EditTask(val args: TaskEditArgs) : NavigationCommand
