package org.jcastrejon.buildlogic

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

@Suppress("UnstableApiUsage")
internal fun Project.configureAndroidLibrary(
    extension: LibraryExtension,
) {

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    extension.apply {
        defaultConfig {
            targetSdk = libs.findVersion("androidTargetSdk").get().toString().toInt()
        }
    }
}
