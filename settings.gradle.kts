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
}

// The library IS the root project — there is no `include(...)`.
//
// This is deliberate: JitPack resolves `com.github.<owner>:<repo>:<tag>`, and keeping the
// published artifactId equal to the repo name is what lets the coordinate stay
// `com.github.erTesla:helium4-theme:<tag>` instead of the multi-module
// `com.github.erTesla.helium4-theme:helium4-theme:<tag>` form.
//
// The demo is a *separate included build* under demo/, not a subproject here, so JitPack
// never sees an Android application module and never tries to build one.
rootProject.name = "helium4-theme"
