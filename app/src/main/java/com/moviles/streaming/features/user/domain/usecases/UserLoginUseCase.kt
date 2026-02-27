package com.moviles.streaming.features.user.domain.usecases

import com.moviles.streaming.features.user.domain.entities.LoginResponse
import com.moviles.streaming.features.user.domain.repositories.UserRepository
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): LoginResponse {
        return userRepository.login(username, password)
    }
}