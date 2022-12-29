import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jcastrejon.buildlogic.configureAndroidCommon
import org.jcastrejon.buildlogic.configureAndroidLibrary

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureAndroidLibrary(this)
                configureAndroidCommon(this)
            }
        }
    }
}