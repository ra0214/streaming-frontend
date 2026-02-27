package com.moviles.streaming.features.chat.domain.entities

data class ChatMessage(
    val type: String,
    val sender: String?,
    val message: String
)


