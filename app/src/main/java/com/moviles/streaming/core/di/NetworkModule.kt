package com.moviles.streaming.core.di

import com.moviles.streaming.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @StreamingRESTRetrofit
    fun provideStreamingRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.backend_rest_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @ChatOkHttpClient
    fun provideChatOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    @WebSocketBaseUrl
    fun provideWebSocketBaseUrl(): String {
        return BuildConfig.backend_ws_url
    }
}
