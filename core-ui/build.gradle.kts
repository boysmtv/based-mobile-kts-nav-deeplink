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
}

apply {
    from("$rootDir/buildConfig/common-builder.gradle")
    from("$rootDir/buildConfig/logging-dependencies.gradle")
}

val customModulePath: groovy.lang.Closure<Any> by ext

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Core.coreKtx)
    implementation(Presentation.constraintLayout)
    implementation(Presentation.material)
    implementation(Presentation.viewPump)
    implementation(Jetpack.lifecycleViewModel)
    implementation(ExternalLib.shimmer)
    implementation(ExternalLib.barteks)
    implementation(ExternalLib.threeTenABP)
    implementation(ExternalLib.flexBox)
    api(ExternalLib.mpChart)
    implementation(customModulePath(CoreModules.coreEntity))
    implementation(Core.appcompat)
    implementation(Presentation.material)
    implementation(Presentation.constraintLayout)
    implementation(Jetpack.navigationFragment)
    implementation(Jetpack.navigationUi)
}