plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.helium4.theme.demo"
    compileSdk { version = release(37) }

    defaultConfig {
        applicationId = "com.helium4.theme.demo"
        // Matches the library floor, so the demo also proves the library really does
        // install and run on its declared minSdk.
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release { isMinifyEnabled = false }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures { compose = true }
}

dependencies {
    // Resolved through the composite build in settings.gradle.kts, so this reads exactly
    // as a real consumer's dependency line would.
    implementation("com.github.erTesla:helium4-theme")

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material.icons.core)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
