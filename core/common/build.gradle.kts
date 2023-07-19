plugins {
    id("todosimply.android.library")
    id("todosimply.jetbrains.kotlin.android")
    id("todosimply.android.hilt")
}

android {
    namespace = "com.fullrandomstudio.todosimply.common"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.timber)
}