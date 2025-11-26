// This script configures all sub-projects and defines plugin versions on the build script classpath.

buildscript {
    repositories {
        google() 
        mavenCentral()
    }
    dependencies {
        // Updated to AGP 8.5.1 and KGP 2.0.0 for maximum compatibility with Gradle 9.2.0
        classpath("com.android.tools.build:gradle:8.5.1") 
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0") 
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
