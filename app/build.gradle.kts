plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.gms)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.passfort"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.passfort"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "35.0.0"
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation("com.valentinilk.shimmer:compose-shimmer:1.0.4")
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // ViewModel provider
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    // Retrofit
    implementation(libs.retrofit)
    // Retrofit with Scalar Converter
    implementation(libs.converter.scalars)
    //Gson
    implementation(libs.gson)
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))
    // Firebase Database
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    //Timber
    implementation(libs.timber)
    // Room library
    implementation(libs.androidx.room.runtime)
    // Room extensions for Kotlin Coroutines, Kotlin Flows
    implementation(libs.androidx.room.ktx)
    // Room codegen
    ksp(libs.androidx.room.compiler)
    // Hilt dependency
    implementation(libs.hilt.android)
    // Hilt navigation
    implementation(libs.androidx.hilt.navigation.compose)
    // Hilt codegen
    ksp(libs.hilt.android.compiler)
    // Kotlin immutable collections
    implementation(libs.kotlinx.collections.immutable)
    // Add extended material icons
    implementation(libs.androidx.material.icons.extended)

}
