package com.fullrandomstudio.todosimply

import android.app.Application
import com.fullrandomstudio.initializer.AppInitializer
import com.fullrandomstudio.todosimply.common.coroutine.ApplicationCoroutineScope
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var appInitializers: Set<@JvmSuppressWildcards AppInitializer>

    @Inject
    @ApplicationCoroutineScope
    lateinit var applicationCoroutineScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())

        // TODO in case of long running initializers move to first run screen
        applicationCoroutineScope.launch {
            AppInitializer.runInitializers(appInitializers.toList(), this@App)
        }
    }
}
