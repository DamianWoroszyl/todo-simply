package com.fullrandomstudio.convention.buildlogic.todosimply

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureAndroidCommon(
    extension: CommonExtension<*, *, *, *>,
) {
    extension.apply {
        compileSdk = 33

        defaultConfig {
            minSdk = 24

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}