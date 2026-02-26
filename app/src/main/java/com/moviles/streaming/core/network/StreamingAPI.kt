package com.moviles.streaming.core.network

import com.moviles.streaming.features.user.data.dataresources.remote.model.LoginRequestDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UserCreateDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UserDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UserLoginResponseDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UsersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StreamingAPI {
    @GET("users/")
    suspend fun getUsers(): UsersResponse

    @POST("users/")
    suspend fun createUser(@Body user: UserCreateDto): UserDto

    @GET("users/username/{username}/")
    suspend fun getUserByUsername(username: String): UserDto

    @POST("auth/login/")
    suspend fun login(@Body request: LoginRequestDto): UserLoginResponseDto
}
