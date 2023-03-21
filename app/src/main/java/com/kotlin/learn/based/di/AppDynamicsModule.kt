/*
 * Copyright © 2020 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.kotlin.learn.based.di

import android.content.Context
import com.kotlin.learn.based.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDynamicsModule {

    @Singleton
    @Provides
    internal fun provideAgentConfiguration(@ApplicationContext application: Context): AgentConfiguration =
        AgentConfiguration.builder()
            .withContext(application.applicationContext)
            .withAppKey(BuildConfig.APPDYNAMICS_APP_KEY)
            .withCollectorURL(BuildConfig.APPDYNAMICS_COLLECTOR_URL)
            .withLoggingEnabled(false)
            .withLoggingLevel(Instrumentation.LOGGING_LEVEL_NONE)
            .withScreenshotsEnabled(false)
            .withInteractionCaptureMode(InteractionCaptureMode.All)
            .build()
}