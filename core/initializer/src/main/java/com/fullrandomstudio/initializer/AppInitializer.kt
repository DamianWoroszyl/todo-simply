package com.fullrandomstudio.initializer

import android.app.Application

interface AppInitializer {

    fun initialize(application: Application)

    companion object {
        fun runInitializers(initializers: List<AppInitializer>, application: Application) {
            initializers.forEach {
                it.initialize(application)
            }
        }
    }
}
