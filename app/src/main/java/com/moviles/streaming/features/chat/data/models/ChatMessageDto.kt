package com.moviles.streaming.features.chat.data.models

data class ChatMessageDto(
    val type: String,
    val sender: String? = null,
    val message: Any? = null
)

data class ChatSendDto(
    val content: String
)


