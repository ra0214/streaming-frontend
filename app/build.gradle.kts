plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.secrets.gradle)
}

android {
    namespace = "com.moviles.streaming"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.moviles.streaming"
        minSdk = 26
        targetSdk = 35
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
        buildConfig = true
        resValues = true
    }

    flavorDimensions.add("environment")

    productFlavors {
        create("dev") {
            dimension = "environment"
            // Leemos de local.properties y nos aseguramos de que tengan comillas dobles
            val restUrl = project.findProperty("backend_rest_url") ?: "http://35.169.196.84:8000/api/"
            val wsUrl = project.findProperty("backend_ws_url") ?: "ws://35.169.196.84:8001/ws/"
            
            buildConfigField("String", "BASE_URL_STREAM", "\"$restUrl\"")
            buildConfigField("String", "BASE_URL_WEBSOCKET", "\"$wsUrl\"")
            resValue("string", "app_name", "Streaming (DEV)")
        }

        create("prod") {
            dimension = "environment"
            val restUrl = project.findProperty("backend_rest_url") ?: "http://35.169.196.84:8000/api/"
            val wsUrl = project.findProperty("backend_ws_url") ?: "ws://35.169.196.84:8001/ws/"
            
            buildConfigField("String", "BASE_URL_STREAM", "\"$restUrl\"")
            buildConfigField("String", "BASE_URL_WEBSOCKET", "\"$wsUrl\"")
            resValue("string", "app_name", "Streaming")
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

configurations.all {
    resolutionStrategy {
        force("androidx.core:core:1.13.1")
        force("androidx.core:core-ktx:1.13.1")
        force("androidx.activity:activity:1.9.3")
        force("androidx.activity:activity-compose:1.9.3")
        force("androidx.navigation:navigation-compose:2.8.3")
        force("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
        force("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    }
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

    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.com.squareup.retrofit2.retrofit)
    implementation(libs.com.squareup.retrofit2.converter.json)
    implementation(libs.com.squareup.okhttp3)
    implementation(libs.io.coil.kt.coil.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
