plugins {
    id("todosimply.android.library")
    id("todosimply.jetbrains.kotlin.android")
    id("todosimply.android.compose")
}

android {
    namespace = "com.fullrandomstudio.todosimply.core.ui"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.javax.inject)
    implementation(libs.androidx.compose.ui.ui)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.ui.tooling.preview)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
