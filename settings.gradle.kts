pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    // IMPORTANT: Removing the explicit 'plugins' block here,
    // as it conflicts with the root build.gradle.kts classpath setup.
}

rootProject.name = "Action-Checker"

// REQUIRED: Include the 'app' module in the build
include(":app")
