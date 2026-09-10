plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    `maven-publish`
}

android {
    namespace = "com.helium4.theme"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        // 24, not 26: nothing in this module calls an API above 24 ungated (the two
        // Build.VERSION checks in AppThemeProvider cover dynamic color and the API 35
        // system-bar deprecation). minSdk lands in the published AAR manifest, and a
        // consumer below it hits a manifest-merger failure they can only work around
        // with tools:overrideLibrary — so keep this as low as the code allows.
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }

    publishing {
        // Required for components["release"] to exist at all; without it the publication
        // below fails to configure. withSourcesJar() because a token-based design system
        // is unusable without being able to read the token definitions.
        singleVariant("release") { withSourcesJar() }
    }

    testOptions {
        // Robolectric needs merged resources and a real AndroidManifest to inflate a
        // window; the screenshot tests do not run without this.
        unitTests.isIncludeAndroidResources = true
    }
}

// Every public declaration must state its visibility and return type. This module is a
// published library: what is `public` here is a promise, so it has to be deliberate
// rather than the result of an omitted keyword.
kotlin {
    explicitApi()
}

dependencies {
    // Deliberately no Hilt, no DataStore, no networking: this module is pure Compose UI
    // so it can be consumed by any app regardless of its DI or persistence choices.

    // `api`, not `implementation`: material3/ui/ui-graphics below are declared without a
    // version and take theirs from this BOM. Exported as `implementation`, the published
    // POM would list those three with no version at all and fail to resolve.
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    // Used directly and appearing in public signatures (Modifier.background, WindowInsets,
    // animateColorAsState), so declared explicitly rather than leaned on transitively.
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.animation)
    // Pinned in the version catalog rather than taken from the BOM. Note this is a
    // `require`, not a `strictly`: a consumer on a newer BOM resolves upward and the pin
    // silently loses. That is intentional — the pin is a floor, not a freeze.
    api(libs.androidx.compose.material.icons.core)

    // WindowCompat only, inside a private function — never in a public signature.
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)

    // Screenshot baseline. Roborazzi is used deps-only, without its Gradle plugin: the
    // plugin hooks the `kotlin-android` plugin, which this build does not apply (AGP 9
    // compiles Kotlin itself). Record with:
    //     ./gradlew :theme:testDebugUnitTest -Proborazzi.record
    // and verify with a plain `./gradlew :theme:testDebugUnitTest`.
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.androidx.compose.ui.test.junit4)
    // Supplies the ComponentActivity that createComposeRule launches. Without it
    // Robolectric fails every test with "Unable to resolve activity for Intent".
    testImplementation(libs.androidx.compose.ui.test.manifest)
}

tasks.withType<Test>().configureEach {
    systemProperty("roborazzi.test.record", providers.gradleProperty("roborazzi.record").isPresent)
    systemProperty("roborazzi.test.verify", !providers.gradleProperty("roborazzi.record").isPresent)
    systemProperty("robolectric.graphicsMode", "NATIVE")
}

// Neither groupId nor version is hardcoded, on purpose.
//
// JitPack invokes the build with `-Pgroup=com.github.<user> -Pversion=<tag>` and then
// harvests ~/.m2 for that exact coordinate. A hardcoded `version = "1.0.0"` would mean
// every later tag builds "successfully" and then 404s on resolution, because nothing was
// ever installed under the coordinate JitPack went looking for.
//
// The fallbacks are only for local `publishToMavenLocal` runs.
//
// `providers.gradleProperty`, not `findProperty`: `group` and `version` are *built-in*
// Project properties, so `findProperty("group")` returns the project's own group (the
// root project name) and never falls through to the value below. That published
// `WavLog:helium4-theme:unspecified` — silently, and it would have 404'd on JitPack.
// `providers.gradleProperty` reads only -P flags and gradle.properties.
group = providers.gradleProperty("group").orNull ?: "com.github.erTesla"
version = providers.gradleProperty("version").orNull ?: "0.0.1-SNAPSHOT"

afterEvaluate {
    publishing.publications.create<MavenPublication>("release") {
        from(components["release"])
        groupId = project.group.toString()
        version = project.version.toString()
        // The one pinned part of the coordinate.
        artifactId = "helium4-theme"

        pom {
            name.set("Helium 4 Theme")
            description.set(
                "A 23-theme, token-based Compose design system. Offline-first, no analytics, " +
                    "no networking, no DI or persistence opinion."
            )
            url.set("https://github.com/erTesla/helium4-theme")
            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://github.com/erTesla/helium4-theme/blob/main/LICENSE")
                }
            }
            developers {
                developer {
                    id.set("erTesla")
                    url.set("https://github.com/erTesla")
                }
            }
            scm {
                url.set("https://github.com/erTesla/helium4-theme")
                connection.set("scm:git:https://github.com/erTesla/helium4-theme.git")
            }
        }
    }
}
