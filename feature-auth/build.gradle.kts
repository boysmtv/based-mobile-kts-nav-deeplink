/*
 * Copyright © 2020 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

plugins {
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    kotlin(BuildPlugins.kapt)
    id(BuildPlugins.navSafeArgs)
}

apply {
    from("$rootDir/buildConfig/common-builder.gradle")
    from("$rootDir/buildConfig/feature-builder.gradle")
    from("$rootDir/buildConfig/dagger-dependencies.gradle")
    from("$rootDir/buildConfig/jetpack-dependencies.gradle")
}

val customModulePath: groovy.lang.Closure<Any> by ext

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(customModulePath(ApiModules.apiAuth))
    implementation(customModulePath(ApiModules.apiSplash))
    testingDependencies()
}