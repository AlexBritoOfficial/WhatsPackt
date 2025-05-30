plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.packt.whatspackt.feature.chat"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
        targetSdk = 34

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    buildToolsVersion = "34.0.0"

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
}

dependencies {
    // 🧩 Project Modules (Shared/Common Layers)
    implementation(project(":common:domain"))
    implementation(project(":common:data"))
    implementation(project(":common:framework"))

    // ⚙️ AndroidX Core + Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose.android)

    // 🎨 Jetpack Compose UI Toolkit
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.android)

    // 🧪 Jetpack Compose Tooling (Preview & Testing)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // 🖼️ Image Loading
    implementation(libs.coil.compose)

    // 🌐 Networking - Ktor
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.ktor.serialization.kotlinx.json)
    // Alternate/duplicate version specified explicitly
    implementation(libs.ktor.client.content.negotiation.v312)

    // 🔧 Dependency Injection - Hilt
    implementation(libs.hilt.android.v255)
    implementation(libs.androidx.hilt.navigation.compose.v100)
    kapt(libs.hilt.compiler.v254)

    // ☁️ Firebase Services
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.auth)
    implementation(libs.google.firebase.analytics)
    implementation(libs.firebase.storage.ktx)

    // ⚙️ Serialization / Utilities
    implementation(libs.gson)
    implementation(libs.javax.inject)
    implementation(libs.androidx.tools.core)

    // 🔁 Background Work
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

    // ✅ Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}