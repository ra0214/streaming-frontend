package com.moviles.streaming.features.chat.presentation.screens

import com.moviles.streaming.features.chat.domain.entities.ChatMessage

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isConnected: Boolean = false,
    val error: String? = null
)