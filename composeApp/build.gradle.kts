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
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                implementation("com.google.code.gson:gson:2.10.1")

                // Dependencias de Ktor para REST API
                implementation("io.ktor:ktor-client-core:2.3.4")
                implementation("io.ktor:ktor-client-cio:2.3.4")
                implementation("io.ktor:ktor-client-json:2.3.4")
                implementation("io.ktor:ktor-client-serialization:2.3.4")
                implementation("io.ktor:ktor-client-logging:2.3.4")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.core.ktx.v1120)
                implementation(libs.androidx.navigation.compose)

                // Firebase para Android
                implementation(enforcedPlatform("com.google.firebase:firebase-bom:33.6.0"))
                implementation("com.google.firebase:firebase-analytics")
                implementation("com.google.firebase:firebase-auth")
                implementation("com.google.firebase:firebase-database")
                implementation("com.google.firebase:firebase-storage")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation("org.jetbrains.compose.desktop:desktop-jvm:1.5.10")
                implementation("io.ktor:ktor-client-cio:2.3.4")
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
