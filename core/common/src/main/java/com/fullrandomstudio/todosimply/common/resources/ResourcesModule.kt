package com.fullrandomstudio.todosimply.common.resources

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ResourcesModule {

    @Binds
    fun provideResourceProvider(provider: DefaultResourceProvider): ResourceProvider
}
