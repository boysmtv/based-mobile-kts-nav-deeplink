plugins {
    id(BuildPlugins.androidApplication)
    kotlin(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    kotlin(BuildPlugins.kapt)
    id(BuildPlugins.hiltPlugin)
}

val customModulePath: groovy.lang.Closure<Any> by ext

android {
    compileSdk = (ConfigData.compileSdkVersion)

    defaultConfig {
        applicationId = "com.kotlin.learn.based"
        minSdk = (ConfigData.minSdkVersion)
        targetSdk = (ConfigData.targetSdkVersion)
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    buildFeatures {
        viewBinding = true
    }

    packagingOptions {
        resources.pickFirsts.add("META-INF/*")
        resources.pickFirsts.add("META-INF/MANIFEST.MF")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))

    implementation(project(CoreModules.core))
    implementation(project(CoreModules.coreUi))
    implementation(project(CoreModules.coreNav))
    implementation(project(CoreModules.coreEntity))
    implementation(project(ApiModules.apiAuth))

    implementation(Core.appDynamicsRuntime)
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
    implementation(project(mapOf("path" to ":core-navigation")))
    implementation(project(mapOf("path" to ":core-entity")))
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