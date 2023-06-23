
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.fullrandomstudio.convention.buildlogic.todosimply.configureAndroidCommon
import com.fullrandomstudio.convention.buildlogic.todosimply.configureAndroidLibrary
import com.fullrandomstudio.convention.buildlogic.todosimply.disableUnnecessaryAndroidTests
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            extensions.configure<LibraryExtension> {
                configureAndroidCommon(this)
                configureAndroidLibrary(this)
            }
        }
    }
}