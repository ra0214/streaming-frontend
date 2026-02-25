plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.moviles.streaming"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.moviles.streaming"
        minSdk = 26
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
        buildConfig = true  //Habilitar variables
        resValues = true
    }

    flavorDimensions.add("environment")
    productFlavors {
        create("dev") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL_STREAM", "\"https://rickandmortyapi.com/api/\"")
            resValue("string", "app_name", "Demo (DEV)")
        }

        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL_STREAM", "\"https://rickandmortyapi.com/api/\"")
            resValue("string", "app_name", "Demo")
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

ksp {
    arg("hilt.disableModulesHaveInstallInCheck", "true")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.ui.text.google.fonts)      // G Fonts
    implementation(libs.androidx.lifecycle.viewmodel.compose)       // viewModel()
    implementation(libs.com.squareup.retrofit2.retrofit)            // Retrofit
    implementation(libs.com.squareup.retrofit2.converter.json)      // JSON
    implementation(libs.io.coil.kt.coil.compose)                    // Coil
    implementation(libs.androidx.navigation.compose)                // Navigation
    implementation(libs.androidx.compose.material.icons.extended)   // Icons extendend
    implementation(libs.hilt.android)                               // Implementación de Hilt
    implementation(libs.hilt.navigation.compose)                    // Integración con Jetpack Compose
    ksp(libs.hilt.compiler)                                         // KSP


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}