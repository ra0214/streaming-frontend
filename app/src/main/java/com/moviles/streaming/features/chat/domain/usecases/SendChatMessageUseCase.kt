package com.moviles.streaming.features.chat.domain.usecases

import com.moviles.streaming.features.chat.domain.entities.ChatMessage
import com.moviles.streaming.features.chat.domain.repositories.ChatRepository
import javax.inject.Inject

class SendChatMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(message: String, streamerId: Int) {
        val optimisticMessage = ChatMessage(
            type = "chat_message",
            sender = "Tú",
            message = message
        )

        val localId = chatRepository.saveMessageLocally(
            message = optimisticMessage,
            streamerId = streamerId,
            isPending = true
        )

        try {
            chatRepository.sendMessage(message)
            chatRepository.deleteLocalMessage(localId)
        } catch (e: Exception) {
            chatRepository.deleteLocalMessage(localId)
            throw e
        }
    }
}
