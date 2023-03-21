package com.kotlin.learn.based.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LanguagePackProviderModule {

    @Binds
    @Singleton
    abstract fun bindsLanguagePackProvider(languagePackRepository: SplashLanguagePackRepository): LanguagePackProvider
}