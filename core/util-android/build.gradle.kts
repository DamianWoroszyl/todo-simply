plugins {
    id("todosimply.android.library")
    id("todosimply.jetbrains.kotlin.android")
    id("todosimply.android.compose")
}

android {
    namespace = "com.fullrandomstudio.todosimply.utilandroid"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewModelCompose)
}
