package com.moviles.streaming.features.user.data.dataresources.remote.model

data class UsersResponse(
    val data: List<UserDto>
)

data class UserDto(
    val id: Int,
    val username: String,
    val rol: String,
)

data class UserCreateDto(
    val username: String,
    val rol: String,
    val password: String
)

data class LoginRequestDto(
    val username: String,
    val password: String
)

data class UserLoginResponseDto(
    val access_token: String,
    val token_type: String,
    val user: UserDto?
)