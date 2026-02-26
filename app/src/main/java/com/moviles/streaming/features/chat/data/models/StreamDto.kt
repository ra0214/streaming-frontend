package com.moviles.streaming.features.chat.data.models

data class StreamDto(
    val streamer_id: Int,
    val username: String,
    val viewers_count: Int
)

data class StreamsResponse(
    val streams: List<StreamDto>
)