plugins {
    id("todosimply.android.application")
    id("todosimply.jetbrains.kotlin.android")
    id("todosimply.android.compose")
    id("todosimply.android.hilt")
}

android {
    namespace = "com.fullrandomstudio.todosimply"

    defaultConfig {
        applicationId = "com.fullrandomstudio.todosimply"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(project(":task:ui"))
    implementation(project(":core:design-system"))
    implementation(project(":core:common"))

    implementation(platform(libs.kotlin.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.stdlib)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.ui.ui.graphics)

    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)

    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}