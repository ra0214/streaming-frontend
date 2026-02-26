package com.moviles.streaming.features.user.domain.usecases

import com.moviles.streaming.features.user.data.dataresources.remote.model.UserDto
import com.moviles.streaming.features.user.domain.entities.User
import com.moviles.streaming.features.user.domain.repositories.UserRepository
import javax.inject.Inject


class UserRegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        username: String,
        rol: String,
        password: String
    ): User {
        return userRepository.register(username, rol, password)
    }
}
