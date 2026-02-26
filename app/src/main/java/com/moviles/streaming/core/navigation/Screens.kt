package com.moviles.streaming.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
data class StreamList(val viewerId: Int)

@Serializable
data class Chat(val streamerId: Int, val viewerId: Int)
