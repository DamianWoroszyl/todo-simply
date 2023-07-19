plugins {
    id("todosimply.android.library")
    id("todosimply.jetbrains.kotlin.android")
}

android {
    namespace = "com.fullrandomstudio.todosimply.initializer"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}