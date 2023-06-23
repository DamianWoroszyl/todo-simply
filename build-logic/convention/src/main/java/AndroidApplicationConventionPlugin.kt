import com.android.build.api.dsl.ApplicationExtension
import com.fullrandomstudio.convention.buildlogic.todosimply.configureAndroidApplication
import com.fullrandomstudio.convention.buildlogic.todosimply.configureAndroidCommon
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension> {
                configureAndroidCommon(this)
                configureAndroidApplication(this)
            }
        }
    }

}