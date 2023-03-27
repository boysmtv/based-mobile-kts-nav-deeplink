buildscript {
    val fl = rootProject.file("api.properties")
    (fl.exists()).let {
        fl.forEachLine {
            rootProject.extra.set(it.split("=")[0], it.split("=")[1])
        }
    }

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
    }
}

allprojects {
    apply(plugin = BuildPlugins.jfrogPlugin)
    apply(plugin = BuildPlugins.mavenPublish)
    apply {
        from("$rootDir/buildConfig/local-aar-config.gradle")
        from("$rootDir/buildConfig/local-aar.gradle")
    }
    apply(plugin = "io.gitlab.arturbosch.detekt")

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

apply {
    from("$rootDir/buildConfig/local-aar-config.gradle")
    from("$rootDir/buildConfig/local-aar.gradle")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
