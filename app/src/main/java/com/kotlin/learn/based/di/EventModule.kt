package com.kotlin.learn.based.di

import com.kotlin.learn.core.common.presentation.events.EventListener
import com.kotlin.learn.based.util.presentation.LearnEvent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EventModule {
    @Provides
    @Singleton
    fun provideEventListener(event: LearnEvent): EventListener = event
}