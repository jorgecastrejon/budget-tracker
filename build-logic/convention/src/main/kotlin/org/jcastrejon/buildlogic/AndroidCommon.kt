package org.jcastrejon.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

@Suppress("UnstableApiUsage")
internal fun Project.configureAndroidCommon(
    extension: CommonExtension<*, *, *, *>,
) {

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    extension.apply {
        compileSdk = libs.findVersion("androidComplieSdk").get().toString().toInt()

        defaultConfig {
            minSdk = libs.findVersion("androidMinSdk").get().toString().toInt()

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        packagingOptions {
            resources {
                excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            }
        }
    }
}