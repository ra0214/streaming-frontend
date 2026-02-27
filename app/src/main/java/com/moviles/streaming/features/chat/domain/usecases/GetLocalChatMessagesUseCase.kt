package com.moviles.streaming.features.chat.domain.usecases

import com.moviles.streaming.features.chat.domain.entities.ChatMessage
import com.moviles.streaming.features.chat.domain.repositories.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalChatMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(streamerId: Int): Flow<List<ChatMessage>> {
        return chatRepository.getLocalMessages(streamerId)
    }
}

