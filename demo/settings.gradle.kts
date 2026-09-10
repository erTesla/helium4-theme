pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "helium4-theme-demo"
include(":app")

// The demo is its own Gradle build, and it consumes the library through a composite
// build rather than through a published artifact. Two reasons:
//
//  1. JitPack builds the *root* build only. If the demo were a subproject of the root,
//     JitPack would try to build an Android application it has no reason to build.
//  2. This is the escape hatch documented in the README: iterating on a token here is
//     edit -> run, not edit -> tag -> wait -> bump.
includeBuild("..") {
    dependencySubstitution {
        substitute(module("com.github.erTesla:helium4-theme")).using(project(":"))
    }
}
