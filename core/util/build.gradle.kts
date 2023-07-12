plugins {
    `java-library`
    id("todosimply.jvm.library")

}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}
