buildscript {

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }

    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.navigationGradlePlugin)
        classpath(BuildPlugins.jfrogExtractor)
        classpath(BuildPlugins.detekt)
        classpath(BuildPlugins.appDynamicsPlugin)
        classpath(BuildPlugins.firebaseCrashlyticsPlugin)
        classpath(BuildPlugins.googlePlayServicePlugin)
        classpath(BuildPlugins.hiltGradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }

}

allprojects {
    apply(plugin = BuildPlugins.jfrogPlugin)
    apply(plugin = BuildPlugins.mavenPublish)
    apply(plugin = BuildPlugins.detektPlugin)
    apply {
        from("$rootDir/buildConfig/local-aar-config.gradle")
        from("$rootDir/buildConfig/local-aar.gradle")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
