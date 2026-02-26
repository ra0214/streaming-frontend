package com.moviles.streaming.core.di

import com.moviles.streaming.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @StreamingRESTRetrofit
    fun provideStreamingRetrofit(): Retrofit {
        return Retrofit.Builder()
<<<<<<< HEAD
            .baseUrl(BuildConfig.BASE_URL_STREAM)
=======
            .baseUrl(BuildConfig.backend_rest_url)
>>>>>>> origin/refactorizar
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @StreamingServerRetrofit
    fun provideStreamingServerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.backend_streaming_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
