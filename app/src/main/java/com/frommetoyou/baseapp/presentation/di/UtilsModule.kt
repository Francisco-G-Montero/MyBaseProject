package com.frommetoyou.baseapp.presentation.di

import com.frommetoyou.baseapp.data.util.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val LOCAL_STORAGE_DEFAULT_NAME = "default_name"
private const val DAIANA_DATABASE = "DaianaDatabase"

@Module
@InstallIn(SingletonComponent::class)
class WrappersModule {
    @Singleton
    @Provides
    fun provideCoroutinesDispatchers(): CoroutinesDispatcherProvider {
        return CoroutinesDispatcherProvider()
    }
}