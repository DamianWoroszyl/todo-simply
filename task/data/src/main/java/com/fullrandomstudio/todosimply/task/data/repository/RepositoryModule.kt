package com.fullrandomstudio.todosimply.task.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindTaskRepository(repository: OfflineTaskRepository): TaskRepository

    @Binds
    fun bindTaskCategoryRepository(repository: OfflineTaskCategoryRepository): TaskCategoryRepository
}
