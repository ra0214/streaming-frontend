package com.moviles.streaming.features.chat.data.dataresources.local.mapper

import com.moviles.streaming.features.chat.data.dataresources.local.entity.ChatMessageEntity
import com.moviles.streaming.features.chat.domain.entities.ChatMessage

fun ChatMessageEntity.toDomain(): ChatMessage {
    return ChatMessage(
        type = type,
        sender = sender,
        message = message
    )
}

fun ChatMessage.toEntity(streamerId: Int, isPending: Boolean = false): ChatMessageEntity {
    return ChatMessageEntity(
        streamerId = streamerId,
        type = type,
        sender = sender,
        message = message,
        isPending = isPending
    )
}

