// Apply the plugins that were defined in settings.gradle.kts
plugins {
    id("com.android.application") // Applies the Android App Plugin
    id("org.jetbrains.kotlin.android") // Applies the Kotlin Android Plugin
}

// --- Android Specific Configuration ---
android {
    // REQUIRED: Specify the target SDK and minimum SDK versions
    namespace = "org.example.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.example.app"
        minSdk = 24 // Android 7.0 (Nougat) or later
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    
    // Set Java/Kotlin compatibility to JDK 17, matching the GitHub Action setup
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
    // Standard UI libraries (for the MainActivity)
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")

    // The Kotlin standard library is often included implicitly but good practice to define
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    
    // Add the project dependency for your application logic
    // We assume 'buildSrc' is meant for your utility code
    // implementation(project(":buildSrc")) 
}
