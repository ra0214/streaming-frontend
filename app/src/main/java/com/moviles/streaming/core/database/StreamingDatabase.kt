package com.moviles.streaming.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moviles.streaming.features.chat.data.dataresources.local.ChatMessageDao
import com.moviles.streaming.features.chat.data.dataresources.local.entity.ChatMessageEntity

@Database(
    entities = [ChatMessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StreamingDatabase : RoomDatabase() {
    abstract fun chatMessageDao(): ChatMessageDao
}

