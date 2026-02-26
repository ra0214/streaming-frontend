package com.moviles.streaming.features.chat.data.dataresources.remote.model

data class StreamDto(
    val streamer_id: Int,
    val username: String,
    val viewers_count: Int
)

data class StreamsResponse(
    val streams: List<StreamDto>
)

data class ChatMessageDto(
    val type: String,
    val sender: String? = null,
    val message: Any? = null
)

data class ChatSendDto(
    val content: String
)
