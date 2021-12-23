package com.frommetoyou.baseapp.presentation.di

import com.frommetoyou.baseapp.data.repository.MPCheckoutRepositoryImpl
import com.frommetoyou.baseapp.data.services.api.MPCheckoutApiService
import com.frommetoyou.baseapp.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.baseapp.domain.repository.MPCheckoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {
    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideLoginRepository(
        mpCheckoutApiService: MPCheckoutApiService,
        coroutinesDispatcherProvider: CoroutinesDispatcherProvider
    ): MPCheckoutRepository {
        return MPCheckoutRepositoryImpl(mpCheckoutApiService, coroutinesDispatcherProvider)
    }
}