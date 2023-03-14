/*
 * Copyright © 2020 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.based

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

@HiltAndroidApp
class LearnApplication : Application() {

//    @Inject
//    lateinit var eventListener: EventListener

//    @Inject
//    lateinit var languagePackProvider: LanguagePackProvider

    override fun onCreate() {
        super.onCreate()
//        AppVersion.VERSION_NAME = BuildConfig.VERSION_NAME
//        AppVersion.VERSION_CODE = BuildConfig.VERSION_CODE

        AndroidThreeTen.init(this)
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
//        initLibrary()
//        ViewPump.init(
//            ViewPump.builder()
//                .addInterceptor(LayoutInflaterInterceptor())
//                .build()
//        )
    }

//    private fun initLibrary() {
//        val supportedLanguage = getSupportedLanguage()
//        Translator.init(languagePackProvider, supportedLanguage)
//    }
//
//    private fun getSupportedLanguage(): SupportedLanguage {
//        return if (languagePackProvider is SplashLanguagePackRepository &&
//            (languagePackProvider as SplashLanguagePackRepository).getDefaultLanguage() == LANGUAGE_EN_US
//        ) SupportedLanguage.ENGLISH
//        else SupportedLanguage.INDONESIAN
//    }
}