// This block tells Gradle where to find the plugin artifacts (like the Android Gradle Plugin)
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    
    // Define the specific versions of the key plugins here
    // This makes the versions available to all modules.
    plugins {
        id("com.android.application") version "8.4.1" // The Android App Plugin
        id("org.jetbrains.kotlin.android") version "1.9.22" // The Kotlin Plugin
    }
}

// Define the name of your root project
rootProject.name = "Action-Checker"

// REQUIRED: Include the 'app' module in the build
include(":app")
