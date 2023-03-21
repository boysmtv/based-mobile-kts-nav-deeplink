plugins {
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    kotlin(BuildPlugins.kapt)
}

android {
    compileSdk = (ConfigData.compileSdkVersion)

    defaultConfig {
        minSdk = (ConfigData.minSdkVersion)
        targetSdk = (ConfigData.targetSdkVersion)
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))

    implementation(project(CoreModules.coreUi))
    implementation(project(CoreModules.coreEntity))
    implementation(Jetpack.roomRuntime)
    implementation(Jetpack.room)
    implementation(Jetpack.lifecycleLiveData)
    implementation(Jetpack.lifecycleViewModel)
    implementation(Jetpack.lifecycleJava)
    implementation(Jetpack.navigationFragment)
    implementation(Jetpack.navigationUi)
    implementation(Jetpack.securityCrypto)
    implementation(Jetpack.paging)
    implementation(Jetpack.workManager)
    kapt(Jetpack.roomCompiler)

    // Network
    implementation(Network.moshiKtx)
    implementation(Network.retrofit)
    implementation(Network.retrofitConverterMoshi)
    implementation(Network.okhttp)
    implementation(Network.okhttpLogging)
    implementation(Network.glide)
    kapt(Network.glideCompiler)
    api(Network.glideOkHttpIntegration) { exclude(group = "glide-parent") }
    kapt(Network.moshiKtxCodegen)

    // External
    implementation(ExternalLib.coroutines)
    implementation(ExternalLib.coroutinesAndroid)

    // Utils
    implementation(ExternalLib.dexter)
    implementation(ExternalLib.threeTenABP)
    implementation(ExternalLib.i18nLanguagePack)

    // Testing
    implementation(Testing.jUnit)
    implementation(Testing.coroutinesTest)

    // Logger
    implementation(ExternalLib.timber)
    implementation(ExternalLib.stetho)
    implementation(ExternalLib.stethoOkhttp)
    implementation(ExternalLib.chucker)

    // Presentation
    implementation(Presentation.viewPump)

    //Document
    implementation(ExternalLib.compressor)
    implementation(ExternalLib.iText)

    //Camera
    implementation(Jetpack.cameraXCore)
    implementation(Jetpack.cameraXView)
    implementation(Jetpack.cameraXLifecycle)
}
