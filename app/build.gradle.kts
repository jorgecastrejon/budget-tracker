plugins {
    id("budget-tracker.android.application")
    id("budget-tracker.jetbrains.kotlin.android")
    id("budget-tracker.android.compose")
    id("budget-tracker.android.hilt")
}

android {
    namespace = "org.jcastrejon.budgettracker"

    defaultConfig {
        applicationId = "org.jcastrejon.budgettracker"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":core:user"))
    implementation(project(":features:login"))
    implementation(project(":features:register"))
    implementation(project(":foundation:design-system"))
    implementation(project(":libraries:database"))

    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore)
    implementation(libs.dagger.hilt.navigation)
    implementation(libs.kotlinx.serialization)

    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.junit)
}