package com.fullrandomstudio.convention.buildlogic.todosimply

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

@Suppress("MagicNumber")
internal fun Project.configureAndroidApplication(
    extension: ApplicationExtension,
) {
    extension.apply {
        defaultConfig {
            targetSdk = TARGET_SDK
        }
    }
}
