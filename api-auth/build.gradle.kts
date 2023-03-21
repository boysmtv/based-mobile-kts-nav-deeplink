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
    from("$rootDir/buildConfig/api-builder.gradle")
    from("$rootDir/buildConfig/dagger-dependencies.gradle")
}

dependencies {
    implementation(project(CoreModules.coreEntity))
    testingDependencies()
}

