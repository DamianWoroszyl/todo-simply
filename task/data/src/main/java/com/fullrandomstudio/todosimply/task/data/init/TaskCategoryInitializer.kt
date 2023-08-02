package com.fullrandomstudio.todosimply.task.data.init

import android.app.Application
import android.content.Context
import androidx.annotation.ArrayRes
import com.fullrandomstudio.initializer.AppInitializer
import com.fullrandomstudio.task.model.TaskCategory
import com.fullrandomstudio.todosimply.common.coroutine.ApplicationScope
import com.fullrandomstudio.todosimply.task.data.R
import com.fullrandomstudio.todosimply.task.data.repository.TaskCategoryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class TaskCategoryInitializer @Inject constructor(
    @ApplicationContext private val appContext: Context,
    @ApplicationScope private val applicationScope: CoroutineScope,
    private val taskCategoryRepository: TaskCategoryRepository
) : AppInitializer {

    override fun initialize(application: Application) {
        applicationScope.launch {
            setupInitialCategories()
        }
    }

    private suspend fun setupInitialCategories() {
        if (taskCategoryRepository.anyExists()) {
            return
        }

        val initialCategories = listOf(
            TaskCategory(
                name = appContext.getString(R.string.base_category_diet),
                color = getColorFromArray(R.array.category_colors_green),
                isDefault = false,
                id = 0
            ),
            TaskCategory(
                name = appContext.getString(R.string.base_category_finance),
                color = getColorFromArray(R.array.category_colors_purple),
                isDefault = false,
                id = 0
            ),
            TaskCategory(
                name = appContext.getString(R.string.base_category_home),
                color = getColorFromArray(R.array.category_colors_orange),
                isDefault = false,
                id = 0
            ),
            TaskCategory(
                name = appContext.getString(R.string.base_category_personal),
                color = getColorFromArray(R.array.category_colors_blue),
                isDefault = false,
                id = 0
            ),
            TaskCategory(
                name = appContext.getString(R.string.base_category_work),
                color = getColorFromArray(R.array.category_colors_cyan),
                isDefault = false,
                id = 0
            ),
            TaskCategory(
                name = appContext.getString(R.string.base_category_workout),
                color = getColorFromArray(R.array.category_colors_red),
                isDefault = false,
                id = 0
            )
        )

        taskCategoryRepository.createAndSaveDefaultCategoryIfDoesntExist()
        taskCategoryRepository.save(initialCategories)
    }

    private fun getColorFromArray(@ArrayRes colorsArray: Int): Int {
        return appContext.resources.getIntArray(
            colorsArray
        )[DEFAULT_COLOR_INDEX]
    }

    companion object {
        private const val DEFAULT_COLOR_INDEX: Int = 2
    }
}
