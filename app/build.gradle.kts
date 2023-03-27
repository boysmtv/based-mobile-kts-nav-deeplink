plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.appDynamicsAdeum)
    id(BuildPlugins.firebaseCrashlytics)
    id(BuildPlugins.googlePlayService)
    kotlin(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    kotlin(BuildPlugins.kapt)
    id(BuildPlugins.hiltPlugin)
}

apply {
    from("$rootDir/buildConfig/common-builder.gradle")
    from("$rootDir/buildConfig/feature-builder.gradle")
    from("$rootDir/buildConfig/api-builder.gradle")
    from("$rootDir/buildConfig/app-dependencies.gradle")
    from("$rootDir/buildConfig/network-dependencies.gradle")
    from("$rootDir/buildConfig/dagger-dependencies.gradle")
    from("$rootDir/buildConfig/jetpack-dependencies.gradle")
    from("$rootDir/buildConfig/reactive-dependencies.gradle")
    from("$rootDir/gradle/install-git-hooks.gradle")
}

val customModulePath: groovy.lang.Closure<Any> by ext

android {
    buildFeatures {
        viewBinding = true
    }

    packagingOptions {
        resources.pickFirsts.add("META-INF/*")
        resources.pickFirsts.add("META-INF/MANIFEST.MF")
    }

    defaultConfig {
        applicationId = "com.kotlin.learn.based"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        externalNativeBuild {
            cmake {
                cppFlags += listOf("")
                arguments += listOf("-DCMAKE_VERBOSE_MAKEFILE:BOOL=ON")
            }
        }

        testInstrumentationRunnerArguments["listener"] = "leakcanary.FailTestOnLeakRunListener"
    }

    externalNativeBuild {
        cmake {
            path("CMakeLists.txt")
        }
    }
    ndkVersion = "22.0.7026061"

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))

    implementation(Core.appDynamicsRuntime)
    implementation(Core.firebaseCrashlytics)
    implementation(Jetpack.roomRuntime)
    implementation(Jetpack.room)
    implementation(Jetpack.lifecycleLiveData)
    implementation(Jetpack.lifecycleViewModel)
    implementation(Jetpack.navigationFragment)
    implementation(Jetpack.navigationUi)
    implementation(Jetpack.securityCrypto)

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

    // Dependency Injection
    implementation(ExternalLib.hiltAndroid)
    kapt(ExternalLib.hiltAndroidCompiler)

    // External
    implementation(ExternalLib.coroutines)
    implementation(ExternalLib.coroutinesAndroid)

    // Utils
    implementation(ExternalLib.threeTenABP)
    implementation(ExternalLib.i18nLanguagePack)
    implementation(Jetpack.lifecycleViewModel)
    implementation(ExternalLib.shimmer)
    implementation(ExternalLib.flexBox)

    // Testing
    implementation(Testing.jUnit)
    implementation(Testing.coroutinesTest)
    androidTestImplementation(Testing.jUnitKtx)
    androidTestImplementation(Testing.testRule)
    androidTestImplementation(Testing.espresso)
    androidTestImplementation(Testing.navigationTesting)
    androidTestImplementation(Testing.espressoContrib)
    androidTestImplementation(Testing.espressoAccessibility)
    androidTestImplementation(Testing.androidxTestTruth)
    androidTestImplementation(Testing.androidTestTruth)
    debugImplementation(Testing.fragmentTesting)
    debugImplementation(Testing.fragmentTestingKtx)
    debugImplementation(Testing.androidxTestCore)
    debugImplementation(Testing.androidxTestRule)
    debugImplementation(Testing.androidxTestRunner)
    debugImplementation(ExternalLib.leakCanary)
    androidTestImplementation(Testing.leakCanaryInstruments)

    // Logger
    implementation(ExternalLib.timber)
    implementation(ExternalLib.stetho)
    implementation(ExternalLib.stethoOkhttp)
    implementation(ExternalLib.chucker)

    // Presentation
    implementation(Presentation.viewPump)

    androidTestImplementation(ExternalLib.magsik)
    testingDependencies()
}

kapt {
    correctErrorTypes = true
}