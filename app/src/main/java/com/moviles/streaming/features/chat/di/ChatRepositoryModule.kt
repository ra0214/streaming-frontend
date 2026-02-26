package com.moviles.streaming.features.chat.di

import com.moviles.streaming.features.chat.data.repositories.ChatRepositoryImp
import com.moviles.streaming.features.chat.domain.repositories.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImp: ChatRepositoryImp
    ): ChatRepository
}
