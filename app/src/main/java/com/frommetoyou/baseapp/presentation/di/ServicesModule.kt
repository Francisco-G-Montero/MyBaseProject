package com.frommetoyou.baseapp.presentation.di

import com.frommetoyou.baseapp.data.services.api.MPCheckoutApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val PRODUCTION_URL  = "https://api.mercadopago.com/"
private const val DEV_URL  = "https://api.mercadopago.com/"
const val BASE_URL = DEV_URL

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {
    @Singleton
    @Provides
    fun provideLoginAPI(httpClient: OkHttpClient): MPCheckoutApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MPCheckoutApiService::class.java)
    }
}