package com.moviles.streaming.features.chat.domain.repositories

<<<<<<< HEAD
interface ChatRepository {
}
=======
import com.moviles.streaming.features.chat.domain.entities.ChatMessage
import com.moviles.streaming.features.chat.domain.entities.Stream
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getActiveStreams(): List<Stream>
    fun connectToStream(streamerId: Int, viewerId: Int): Flow<ChatMessage>
    fun sendMessage(message: String)

    fun disconnect()
}


>>>>>>> origin/refactorizar
