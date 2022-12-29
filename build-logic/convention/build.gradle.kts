plugins {
    `kotlin-dsl`
}

group = "org.jcastrejon.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "budget-tracker.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidCompose") {
            id = "budget-tracker.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "budget-tracker.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidKotlin") {
            id = "budget-tracker.jetbrains.kotlin.android"
            implementationClass = "AndroidKotlinConventionPlugin"
        }
        register("androidLibrary") {
            id = "budget-tracker.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}