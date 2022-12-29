plugins {
    id("budget-tracker.android.library")
    id("budget-tracker.jetbrains.kotlin.android")
    id("budget-tracker.android.compose")
    id("budget-tracker.android.hilt")
}

android {
    namespace = "org.jcastrejon.features.login"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
}

dependencies {
    implementation(project(":core:user"))
    implementation(project(":foundation:design-system"))

    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.dagger.hilt.navigation)

    //test
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.espresso.core)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockk)
}