plugins {
    id("todosimply.android.library")
    id("todosimply.jetbrains.kotlin.android")
    id("todosimply.android.hilt")
}

android {
    namespace = "com.fullrandomstudio.todosimply.task.data"
}

dependencies {
    implementation(project(":task:model"))
    implementation(project(":task:database"))
    implementation(project(":core:common"))
    implementation(project(":core:initializer"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.timber)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
}
