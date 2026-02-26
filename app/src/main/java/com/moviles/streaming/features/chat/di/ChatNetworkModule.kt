package com.moviles.streaming.features.chat.di

import com.moviles.streaming.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatNetworkModule {

    @Provides
    @Singleton
    @Named("ChatOkHttpClient")
    fun provideChatOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("WebSocketBaseUrl")
    fun provideWebSocketBaseUrl(): String {
        return BuildConfig.backend_ws_url
    }
}