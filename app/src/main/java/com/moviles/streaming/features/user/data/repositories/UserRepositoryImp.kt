package com.moviles.streaming.features.user.data.repositories

import com.moviles.streaming.core.network.StreamingAPI
import com.moviles.streaming.features.user.data.dataresources.remote.mapper.toDomain
import com.moviles.streaming.features.user.data.dataresources.remote.model.LoginRequestDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UserCreateDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UserLoginResponseDto
import com.moviles.streaming.features.user.domain.entities.User
import com.moviles.streaming.features.user.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val streamingAPI: StreamingAPI
): UserRepository {

    override suspend fun register(
        username: String,
        rol: String,
        password: String
    ): User {
        val userCreateDto = UserCreateDto(
            username = username,
            rol = rol,
            password = password
        )
        return streamingAPI.createUser(userCreateDto).toDomain()
    }

    override suspend fun login(
        username: String,
        password: String
    ): UserLoginResponseDto {
        return streamingAPI.login(
            LoginRequestDto(
                username = username,
                password = password
            )
        )
    }
}
