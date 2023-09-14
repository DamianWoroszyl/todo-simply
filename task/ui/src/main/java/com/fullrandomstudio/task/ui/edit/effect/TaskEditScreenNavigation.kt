package com.fullrandomstudio.task.ui.edit.effect

import com.fullrandomstudio.core.ui.effect.Effect

sealed interface TaskEditScreenNavigation : Effect

object TaskPrepareErrorPop : TaskEditScreenNavigation

object PopTaskEdit : TaskEditScreenNavigation
