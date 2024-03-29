plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.parcelize)
}

kotlin {
    applyDefaultHierarchyTemplate()

    jvm {
        sourceSets["jvmMain"].resources.srcDirs("src/main/res")
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.jvmTarget.get()
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared" // Used in app-ios-swift

            export(libs.decompose.decompose)
            export(libs.essenty.lifecycle)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.decompose.decompose)
                api(libs.essenty.lifecycle)
                api(libs.mvi.kotlin)
                api(libs.mvi.kotlin.main)
                api(libs.mvi.kotlin.coroutine.ext)
                api(libs.koin.core)
                api(libs.coroutine.core)
            }
        }
        val jvmMain by getting {
            dependencies {
                api(libs.coroutine.swing)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.example.myapplication.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    dependencies {
        api(libs.koin.android)
    }

    sourceSets["main"].resources.srcDirs("src/main/res")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}