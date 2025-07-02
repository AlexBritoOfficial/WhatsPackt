plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android") version "2.0.0" // ✅ downgrade from 2.1.x to 2.0.0
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"

}

android {
    namespace = "com.packt.create_chat"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

}

dependencies {

    implementation(project(":common:domain"))
    implementation(project(":common:framework"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:conversations"))
    implementation(project(":feature:chat"))

    // Hilt
    implementation(libs.hilt.android.v255)
    implementation(libs.androidx.hilt.navigation.compose.v100)
    testImplementation(libs.testng)
    kapt(libs.hilt.compiler.v254)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    // Compose
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Optional: force kotlinx-metadata-jvm 0.9.0 in case something tries to pull old versions
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.9.0")
}
