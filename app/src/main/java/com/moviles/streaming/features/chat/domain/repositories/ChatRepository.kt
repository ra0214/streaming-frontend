package com.moviles.streaming.features.chat.domain.repositories

import com.moviles.streaming.features.chat.domain.entities.ChatMessage
import com.moviles.streaming.features.chat.domain.entities.Stream
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getActiveStreams(): List<Stream>

    fun connectToStream(streamerId: Int, viewerId: Int): Flow<Unit>
    fun sendMessage(message: String)
    fun disconnect()
    fun getLocalMessages(streamerId: Int): Flow<List<ChatMessage>>
    suspend fun saveMessageLocally(message: ChatMessage, streamerId: Int, isPending: Boolean = false): Long
    suspend fun deleteLocalMessage(id: Long)
}