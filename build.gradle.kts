buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath("com.google.gms:google-services:4.3.15")
    }
}


// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
    alias(libs.plugins.google.gms.google.services) apply false
    kotlin("plugin.serialization") version "2.0.0" apply false
}
