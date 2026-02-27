package com.moviles.streaming.features.user.domain.entities

data class LoginResponse(
    val accessToken: String,
    val tokenType: String,
    val user: User?
)

