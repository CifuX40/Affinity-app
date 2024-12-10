import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform") version "2.0.21"
    id("org.jetbrains.compose") version "1.7.0"
    id("com.android.application")
    kotlin("plugin.serialization") version "1.9.10"
    id("com.google.gms.google-services")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    jvm("desktop") {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.gson)

                // Dependencias de Ktor para REST API
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.json)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.logging)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.core.ktx)
                implementation(libs.androidx.navigation.compose)

                // Firebase para Android
                implementation(libs.firebase.bom)
                implementation(libs.google.firebase.analytics)
                implementation(libs.google.firebase.auth)
                implementation(libs.google.firebase.database)
                implementation(libs.google.firebase.storage)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.desktop.jvm)
                implementation(libs.ktor.client.cio)
            }
        }
    }
}

android {
    namespace = "compose.project.demo"
    compileSdk = 35
    defaultConfig {
        applicationId = "compose.project.demo"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
}

compose.desktop {
    application {
        mainClass = "org.appaffinity.project.MainKt"
        nativeDistributions {
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Deb
            )
            packageName = "compose.project.demo"
            packageVersion = "1.0.0"
        }
    }
}

apply(plugin = "com.google.gms.google-services")
