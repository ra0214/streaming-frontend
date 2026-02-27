package com.moviles.streaming.features.user.domain.repositories

import com.moviles.streaming.features.user.domain.entities.LoginResponse
import com.moviles.streaming.features.user.domain.entities.User

interface UserRepository {
    suspend fun login(username: String, password: String): LoginResponse
    suspend fun register(username: String, rol: String, password: String): User
}