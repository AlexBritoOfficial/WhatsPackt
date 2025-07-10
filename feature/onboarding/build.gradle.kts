plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

}

android {
    namespace = "com.packt.onboarding"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7" // Replace with version matching your Compose BOM
    }
}

dependencies {

    implementation(project(":common:framework"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.accompanist.pager.v0360)

    // Compose BOM and UI dependencies (only one BOM declaration)
    implementation(platform(libs.androidx.compose.bom.v20230601))

    implementation(libs.foundation) // from BOM
    implementation(libs.material3)  // from BOM
    implementation(libs.androidx.compose.ui.ui)                 // from BOM
    implementation(libs.ui.graphics)        // from BOM
    implementation(libs.ui.tooling.preview) // from BOM
    implementation(libs.androidx.runtime)       // from BOM
    implementation(libs.activity.compose)     // from BOM

    debugImplementation(libs.ui.tooling)        // from BOM
    debugImplementation(libs.ui.test.manifest)  // from BOM
    androidTestImplementation(libs.androidx.compose.bom.v20250601)
    androidTestImplementation(libs.ui.test.junit4) // from BOM

}