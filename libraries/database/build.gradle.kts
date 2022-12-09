plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.security.crypto)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)
}