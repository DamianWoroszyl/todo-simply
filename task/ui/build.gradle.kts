plugins {
    id("todosimply.android.library")
    id("todosimply.jetbrains.kotlin.android")
    id("todosimply.android.compose")
    id("todosimply.android.hilt")
}

android {
    namespace = "com.fullrandomstudio.todosimply.task.ui"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:common"))
    implementation(project(":core:util"))
    implementation(project(":core:util-android"))
    implementation(project(":core:ui"))
    implementation(project(":task:data"))
    implementation(project(":task:domain"))
    implementation(project(":task:model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.immutable.collections)

    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.ui.ui.graphics)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.ui.tooling.preview)

    implementation(libs.androidx.constraint.layout.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
}
