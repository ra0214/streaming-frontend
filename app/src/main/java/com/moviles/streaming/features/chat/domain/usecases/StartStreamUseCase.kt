package com.moviles.streaming.features.chat.domain.usecases

import com.moviles.streaming.features.chat.domain.entities.ChatMessage
import com.moviles.streaming.features.chat.domain.repositories.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartStreamUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(streamerId: Int): Flow<ChatMessage> {
        return chatRepository.startStream(streamerId)
    }
}

