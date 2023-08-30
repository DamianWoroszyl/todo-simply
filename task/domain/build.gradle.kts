plugins {
    id("todosimply.android.library")
    id("todosimply.jetbrains.kotlin.android")
}

android {
    namespace = "com.fullrandomstudio.todosimply.task.domain"
}

dependencies {
    implementation(project(":task:model"))
    implementation(project(":task:data"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.javax.inject)
}
