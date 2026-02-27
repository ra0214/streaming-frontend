package com.moviles.streaming.features.chat.data.dataresources.remote.mapper

import com.moviles.streaming.features.chat.data.dataresources.remote.model.ChatMessageDto
import com.moviles.streaming.features.chat.data.dataresources.remote.model.StreamDto
import com.moviles.streaming.features.chat.domain.entities.ChatMessage
import com.moviles.streaming.features.chat.domain.entities.Stream

fun ChatMessageDto.toDomain(): ChatMessage {
    return ChatMessage(
        type = type,
        sender = sender,
        message = message?.content ?: ""
    )
}

fun StreamDto.toDomain(): Stream {
    return Stream(
        streamerId = this.streamer_id,
        username = this.username,
        viewersCount = this.viewers_count
    )
}

