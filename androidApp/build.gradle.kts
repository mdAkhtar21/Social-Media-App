plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version "1.9.10"

}

android {
    namespace = "com.example.socialmedialapp.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.socialmedialapp.android"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // This dependency is correct here because androidApp uses the shared module.
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.room.runtime.android)
    debugImplementation(libs.compose.ui.tooling)
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)
    implementation("com.google.accompanist:accompanist-swiperefresh:0.34.0")

    implementation("io.github.raamcosta.compose-destinations:core:1.10.0")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.10.0")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("io.insert-koin:koin-androidx-compose:3.5.0")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

    implementation("io.coil-kt:coil-compose:2.6.0")
}