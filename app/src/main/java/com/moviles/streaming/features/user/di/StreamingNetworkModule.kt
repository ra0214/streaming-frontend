package com.moviles.streaming.features.user.di

import com.moviles.streaming.core.network.StreamingAPI
import com.moviles.streaming.core.di.StreamingRESTRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object StreamingNetworkModule {
    @Provides
    @Singleton
    fun provideStreamingAPI(@StreamingRESTRetrofit retrofit: Retrofit): StreamingAPI {
        return retrofit.create(StreamingAPI::class.java)
    }
}