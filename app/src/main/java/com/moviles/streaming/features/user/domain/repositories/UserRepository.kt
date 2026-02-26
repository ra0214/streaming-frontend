package com.moviles.streaming.features.user.domain.repositories

import com.moviles.streaming.features.user.data.dataresources.remote.model.UserDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UserLoginResponseDto
import com.moviles.streaming.features.user.domain.entities.User

interface UserRepository {
    suspend fun login(username: String, password: String): UserLoginResponseDto
    suspend fun register(username: String, rol: String, password: String): User
}