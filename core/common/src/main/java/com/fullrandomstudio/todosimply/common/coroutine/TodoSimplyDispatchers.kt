package com.fullrandomstudio.todosimply.common.coroutine

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: TodoSimplyDispatchers)

enum class TodoSimplyDispatchers {
    Main,
    Default,
    IO,
}
