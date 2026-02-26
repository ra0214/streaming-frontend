package com.moviles.streaming.core.network

import com.moviles.streaming.features.user.data.dataresources.remote.model.UserDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UsersResponse
import com.moviles.streaming.features.user.domain.entities.User
import retrofit2.http.GET
import retrofit2.http.POST

interface StreamingAPI {
    @GET("users/")
    suspend fun getUsers(): UsersResponse

    @POST("users/")
    suspend fun createUser(user: User): UserDto

    @GET("users/username/{username}/")
    suspend fun getUserByUsername(username: String): UserDto

    @POST("auth/login/")
    suspend fun login(username: String, password: String): UserDto
}