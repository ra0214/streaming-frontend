package com.moviles.streaming.features.chat.domain.usecases

import com.moviles.streaming.features.chat.domain.repositories.ChatRepository
import javax.inject.Inject

class SendChatMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(message: String) {
        chatRepository.sendMessage(message)
    }
}


