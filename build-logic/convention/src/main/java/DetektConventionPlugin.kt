
import com.fullrandomstudio.convention.buildlogic.todosimply.configureDetekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class DetektConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            extensions.configure<DetektExtension> {
                configureDetekt(this)
            }

            /*
            todo dw add detekt formatting?
            dependencies {
                "detektPlugins"(libs.findLibrary("detekt-formatting").get())
            }
            */
        }
    }
}
