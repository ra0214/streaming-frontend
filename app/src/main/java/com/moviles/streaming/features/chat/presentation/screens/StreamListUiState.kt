package com.moviles.streaming.features.chat.presentation.screens

import com.moviles.streaming.features.chat.domain.entities.Stream

data class StreamListUiState(
    val streams: List<Stream> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)