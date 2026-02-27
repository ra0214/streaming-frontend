package com.moviles.streaming.features.chat.domain.usecases

import com.moviles.streaming.features.chat.domain.repositories.ChatRepository
import javax.inject.Inject

class DisconnectChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke() {
        chatRepository.disconnect()
    }
}


