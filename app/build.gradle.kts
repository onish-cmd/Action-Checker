// Apply the plugins. Note the change in Kotlin plugin syntax.
plugins {
    id("com.android.application")
    // Using the Kotlin DSL standard library function for the plugin ID
    kotlin("android") 
}

// --- Android Specific Configuration ---
android {
    namespace = "org.example.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.example.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    
    // Set compatibility to the latest version supported by Android 34/Gradle 9.2.0
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

// --- Dependencies ---
dependencies {
    // Core Kotlin dependency
    implementation(kotlin("stdlib"))
    
    // Standard Android UI libraries
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
}
