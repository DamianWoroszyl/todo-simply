package com.fullrandomstudio.convention.buildlogic.todosimply

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project

@Suppress("MagicNumber")
internal fun Project.configureAndroidLibrary(
    extension: LibraryExtension,
) {
    extension.apply {
        defaultConfig.targetSdk = TARGET_SDK
    }
}
