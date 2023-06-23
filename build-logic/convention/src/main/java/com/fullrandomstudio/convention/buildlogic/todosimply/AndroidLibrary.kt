package com.fullrandomstudio.convention.buildlogic.todosimply

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project

internal fun Project.configureAndroidLibrary(
    extension: LibraryExtension,
) {
    extension.apply {
        defaultConfig.targetSdk = 33
    }
}