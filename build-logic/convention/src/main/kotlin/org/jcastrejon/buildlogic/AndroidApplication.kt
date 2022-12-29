package org.jcastrejon.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidApplication(
    extension: ApplicationExtension,
) {

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    extension.apply {
        defaultConfig {
            targetSdk = libs.findVersion("androidTargetSdk").get().toString().toInt()
        }
    }
}