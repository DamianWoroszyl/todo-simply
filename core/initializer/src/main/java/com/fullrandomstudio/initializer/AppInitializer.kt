package com.fullrandomstudio.initializer

import android.app.Application

interface AppInitializer {

    suspend fun initialize(application: Application)

    companion object {
        suspend fun runInitializers(initializers: List<AppInitializer>, application: Application) {
            initializers.forEach {
                it.initialize(application)
            }
        }
    }
}
