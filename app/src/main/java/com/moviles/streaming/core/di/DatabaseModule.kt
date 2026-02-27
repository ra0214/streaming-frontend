package com.moviles.streaming.core.di

import android.content.Context
import androidx.room.Room
import com.moviles.streaming.core.database.StreamingDatabase
import com.moviles.streaming.features.chat.data.dataresources.local.ChatMessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): StreamingDatabase {
        return Room.databaseBuilder(
            context,
            StreamingDatabase::class.java,
            "streaming_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideChatMessageDao(database: StreamingDatabase): ChatMessageDao {
        return database.chatMessageDao()
    }
}

