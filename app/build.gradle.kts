plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "st.masoom.easyfood"
    compileSdk = 34

    defaultConfig {
        applicationId = "st.masoom.easyfood"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation ("io.coil-kt:coil-compose:2.x.x")
    implementation ("androidx.navigation:navigation-compose:2.7.0")
    implementation ("androidx.compose.material3:material3:1.1.0")
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    implementation ("com.intuit.ssp:ssp-android:1.1.0")
    implementation ("androidx.compose.material3:material3:<latest-version>")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.25")
    implementation ("io.coil-kt:coil-compose:2.4.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // This is the latest stable Retrofit version as of now.
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Updated Gson converter to match Retrofit's latest version.

    implementation("com.github.bumptech.glide:glide:4.15.1") // This is the latest stable version of Glide.

    implementation ("androidx.compose.ui:ui:1.5.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Define your lifecycle version

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") // ViewModel KTX
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") // LiveData KTX
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2") // Lifecycle Runtime KTX
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.2")

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}