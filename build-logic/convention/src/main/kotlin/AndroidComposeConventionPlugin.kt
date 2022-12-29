import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jcastrejon.buildlogic.configureAndroidCompose

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.findByType(ApplicationExtension::class.java)?.apply {
                configureAndroidCompose(this)
            }
            extensions.findByType(LibraryExtension::class.java)?.apply {
                configureAndroidCompose(this)
            }
        }
    }
}