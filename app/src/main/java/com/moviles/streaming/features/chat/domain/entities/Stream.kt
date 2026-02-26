package com.moviles.streaming.features.chat.domain.entities

data class Stream(
    val streamerId: Int,
    val username: String,
    val viewersCount: Int
)

