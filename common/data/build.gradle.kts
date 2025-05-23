plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")

}

android {
    namespace = "com.packt.whatspackt.common.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 28
        targetSdk = 33

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
}

dependencies {
    implementation(project(":common:domain"))
    implementation(project(":common:framework"))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.messaging.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //Hilt
    implementation (libs.hilt.android.v255)
    implementation (libs.androidx.hilt.navigation.compose.v100)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    kapt (libs.hilt.compiler.v254)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation("com.google.firebase:firebase-auth:23.2.0")
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.1")

    // Room
    // https://mvnrepository.com/artifact/androidx.room/room-runtime
    implementation("androidx.room:room-runtime:2.7.0")
    // https://mvnrepository.com/artifact/androidx.room/room-common
    implementation("androidx.room:room-common:2.7.0")
    // https://mvnrepository.com/artifact/androidx.room/room-ktx
    implementation("androidx.room:room-ktx:2.7.0")
    // https://mvnrepository.com/artifact/androidx.room/room-compiler
    kapt("androidx.room:room-compiler:2.7.0")


}