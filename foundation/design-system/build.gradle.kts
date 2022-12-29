plugins {
    id("budget-tracker.android.library")
    id("budget-tracker.jetbrains.kotlin.android")
    id("budget-tracker.android.compose")
}

android {
    namespace = "org.jcastrejon.designsystem"

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
}

dependencies {

    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    api(libs.androidx.compose.activity)
    api(libs.androidx.compose.navigation)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material.icons)
    api(libs.androidx.compose.material.icons.extended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)

    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.junit)
}