package com.moviles.streaming.features.chat.domain.usecases

import com.moviles.streaming.features.chat.domain.repositories.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConnectToChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(streamerId: Int, viewerId: Int): Flow<Unit> {
        return chatRepository.connectToStream(streamerId, viewerId)
    }
}
