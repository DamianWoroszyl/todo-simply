package com.fullrandomstudio.todosimply.task.data.init

import com.fullrandomstudio.initializer.AppInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface InitModule {

    @Binds
    @IntoSet
    fun bindTaskCategoryInitializer(taskCategoryInitializer: TaskCategoryInitializer): AppInitializer

}
