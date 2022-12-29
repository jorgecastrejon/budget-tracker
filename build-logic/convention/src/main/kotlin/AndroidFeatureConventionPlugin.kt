import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jcastrejon.buildlogic.configureAndroidCommon
import org.jcastrejon.buildlogic.configureAndroidLibrary
import org.jcastrejon.buildlogic.kotlinOptions

@Suppress("UnstableApiUsage")
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("budget-tracker.android.library")
                apply("budget-tracker.jetbrains.kotlin.android")
                apply("budget-tracker.android.compose")
                apply("budget-tracker.android.hilt")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                kotlinOptions {
                    freeCompilerArgs = freeCompilerArgs + listOf(
                        "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                        "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                        "-Xopt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi"
                    )
                }

                testOptions {
                    unitTests.apply {
                        isIncludeAndroidResources = true
                    }
                }

                configureAndroidLibrary(this)
                configureAndroidCommon(this)

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                dependencies {
                    "implementation"(libs.findLibrary("dagger.hilt.navigation").get())
                }
            }
        }
    }
}
