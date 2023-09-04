pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "todo-simply"
include(":app")

include(":core")
include(":core:design-system")
include(":core:util")

include(":task")
include(":task:data")
include(":task:ui")

include(":task:domain")
include(":task:database")
include(":task:model")
include(":core:util-android")
include(":core:initializer")
include(":core:common")
include(":core:ui")
