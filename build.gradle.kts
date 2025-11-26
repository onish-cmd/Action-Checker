// This script configures all sub-projects and defines plugin versions on the build script classpath.

buildscript {
    repositories {
        google() // Essential for finding Android dependencies
        mavenCentral()
    }
    dependencies {
        // DEFINE THE PLUGINS AS CLASS PATH DEPENDENCIES HERE
        // This is the most reliable way to resolve the "BaseVariant" error.
        classpath("com.android.tools.build:gradle:8.4.1") 
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22") 
    }
}

// Global settings for all projects (optional, but good practice)
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// Define tasks to remove generated files
tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
