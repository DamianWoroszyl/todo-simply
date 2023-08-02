plugins {
    id("todosimply.android.library")
    id("todosimply.jetbrains.kotlin.android")
    id("todosimply.android.hilt")
    id("todosimply.android.room")
}

android {
    namespace = "com.fullrandomstudio.todosimply.task.database"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
}
