pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
rootProject.name = "learn-based"
include(":app")
include(":core")
include(":core-ui")
include(":core-navigation")
include(":api-splash")
include(":api-auth")
include(":feature-auth")
include(":core-entity")
