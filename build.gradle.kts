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
        classpath(BuildPlugins.hiltGradlePlugin)
    }

}

allprojects {
    apply(plugin = BuildPlugins.jfrogPlugin)
    apply(plugin = BuildPlugins.mavenPublish)
    apply(plugin = BuildPlugins.detektPlugin)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
