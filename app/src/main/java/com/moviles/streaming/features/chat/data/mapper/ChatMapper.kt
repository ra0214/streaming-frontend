package com.moviles.streaming.features.chat.data.mapper

import com.moviles.streaming.features.chat.data.models.ChatMessageDto
import com.moviles.streaming.features.chat.data.models.StreamDto
import com.moviles.streaming.features.chat.domain.entities.ChatMessage
import com.moviles.streaming.features.chat.domain.entities.Stream

fun ChatMessageDto.toDomain(): ChatMessage {
    val content = when (message) {
        is String -> message
        is Map<*, *> -> message["content"]?.toString() ?: message.toString()
        else -> message?.toString() ?: ""
    }
    return ChatMessage(
        type = this.type,
        sender = this.sender,
        message = content
    )
}

fun StreamDto.toDomain(): Stream {
    return Stream(
        streamerId = this.streamer_id,
        username = this.username,
        viewersCount = this.viewers_count
    )
}

