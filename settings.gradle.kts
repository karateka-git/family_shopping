pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "family_shopping"
include(":shared")
include(":compose-ui")
include(":app-android")
include(":app-desktop")
