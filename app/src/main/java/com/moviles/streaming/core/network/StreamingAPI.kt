package com.moviles.streaming.core.network

import com.moviles.streaming.features.chat.data.models.StreamsResponse
import com.moviles.streaming.features.user.data.dataresources.remote.model.UserCreateDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UserDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UserLoginResponseDto
import com.moviles.streaming.features.user.data.dataresources.remote.model.UsersResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StreamingAPI {
    @GET("users/")
    suspend fun getUsers(): UsersResponse

    @POST("users/")
    suspend fun createUser(@Body user: UserCreateDto): UserDto

    @GET("users/username/{username}/")
    suspend fun getUserByUsername(@Path("username") username: String): UserDto

    @FormUrlEncoded
    @POST("auth/login/")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): UserLoginResponseDto

    @GET("streams/active/")
    suspend fun getActiveStreams(): StreamsResponse
}