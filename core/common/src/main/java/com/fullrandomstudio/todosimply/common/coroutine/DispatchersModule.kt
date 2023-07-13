package com.fullrandomstudio.todosimply.common.coroutine

import com.fullrandomstudio.todosimply.common.coroutine.TodoSimplyDispatchers.Default
import com.fullrandomstudio.todosimply.common.coroutine.TodoSimplyDispatchers.IO
import com.fullrandomstudio.todosimply.common.coroutine.TodoSimplyDispatchers.Main
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(Main)
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
