package com.moviles.streaming.features.chat.di

import com.moviles.streaming.core.di.StreamingServerRetrofit
import com.moviles.streaming.core.network.StreamingWsAPI
import com.moviles.streaming.features.chat.data.repositories.ChatRepositoryImp
import com.moviles.streaming.features.chat.domain.repositories.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatRepositoryModule {

    @Provides
    @Singleton
    fun provideChatRepository(
        @StreamingServerRetrofit retrofit: Retrofit,
        @Named("ChatOkHttpClient") okHttpClient: OkHttpClient,
        @Named("WebSocketBaseUrl") baseWsUrl: String
    ): ChatRepository {
        val streamingWsAPI = retrofit.create(StreamingWsAPI::class.java)
        return ChatRepositoryImp(streamingWsAPI, okHttpClient, baseWsUrl)
    }
}
