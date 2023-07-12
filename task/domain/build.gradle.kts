plugins {
    `java-library`
    id("todosimply.jvm.library")
}

dependencies {
    implementation(project(":task:model"))

    implementation(libs.javax.inject)
}
