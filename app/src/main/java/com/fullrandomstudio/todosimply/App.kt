package com.fullrandomstudio.todosimply

import android.app.Application
import com.fullrandomstudio.initializer.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var appInitializers: Set<@JvmSuppressWildcards AppInitializer>

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())
        AppInitializer.runInitializers(appInitializers.toList(), this)
    }
}
