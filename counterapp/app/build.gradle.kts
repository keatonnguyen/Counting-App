plugins {
    alias(libs.plugins.android.application)
    // Add this ONLY if you will write Kotlin code files (.kt). Safe to keep even if you're Java-only.
    // alias(libs.plugins.kotlin.android)
}

android {
    namespace = "keaton.counterapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "keaton.counterapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Use Java 17 (AGP 8.x is happiest here)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    // If Android Studio shows a "Gradle JDK" setting, set it to JDK 17 as well.

    // (Optional) If you ever use ViewBinding:
    // buildFeatures { viewBinding = true }
}

dependencies {
    // AndroidX + Material (via your version catalog)
    implementation(libs.appcompat)            // e.g., 1.7.0
    implementation(libs.material)             // e.g., 1.12.0 (needed for Theme.Material3.*)
    implementation(libs.activity)             // e.g., 1.9.x
    implementation(libs.constraintlayout)     // e.g., 2.1.4

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
