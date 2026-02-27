package com.moviles.streaming.features.chat.data.dataresources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moviles.streaming.features.chat.data.dataresources.local.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity): Long

    @Query("SELECT * FROM chat_messages WHERE streamerId = :streamerId ORDER BY timestamp ASC")
    fun getMessagesByStreamer(streamerId: Int): Flow<List<ChatMessageEntity>>

    @Query("DELETE FROM chat_messages WHERE id = :id")
    suspend fun deleteMessage(id: Long)

    @Query("DELETE FROM chat_messages WHERE streamerId = :streamerId")
    suspend fun clearMessagesForStream(streamerId: Int)
}

