package com.moviles.streaming.features.chat.domain.usecases

import com.moviles.streaming.features.chat.domain.entities.Stream
import com.moviles.streaming.features.chat.domain.repositories.ChatRepository
import javax.inject.Inject

class GetActiveStreamsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): List<Stream> {
        return chatRepository.getActiveStreams()
    }
}

