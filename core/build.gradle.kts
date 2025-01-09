import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.google.gms.google.services)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.alexius.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val properties = gradleLocalProperties(rootDir, providers = project.providers)
        buildConfigField("String", "WEB_CLIENT_ID", "\"${properties.getProperty("WEB_CLIENT_ID")}\"")
        buildConfigField("String", "SPEECHAI_API_KEY", "\"${properties.getProperty("SPEECHAI_API_KEY")}\"")
        buildConfigField("String", "GEMINI_API_KEY", "\"${properties.getProperty("GEMINI_API_KEY")}\"")
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

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    api(platform("com.google.firebase:firebase-bom:33.7.0"))

    api("com.google.firebase:firebase-auth")
    // Also add the dependency for the Google Play services library and specify its version
    api("com.google.android.gms:play-services-auth:21.3.0")

    //Credential Manager
    api("androidx.credentials:credentials:1.5.0-beta01")
    // optional - needed for credentials support from play services, for devices running
    // Android 13 and below.
    api("androidx.credentials:credentials-play-services-auth:1.5.0-beta01")
    api ("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    //Datastore
    implementation (libs.androidx.datastore.preferences)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    //OkHttp Logging Interceptor
    implementation(libs.logging.interceptor)

    //Room
    implementation (libs.androidx.room.runtime)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    ksp (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    api(libs.androidx.hilt.navigation.compose)
    ksp(libs.androidx.hilt.compiler)

    //Gemini
    api("com.google.ai.client.generativeai:generativeai:0.9.0")

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}