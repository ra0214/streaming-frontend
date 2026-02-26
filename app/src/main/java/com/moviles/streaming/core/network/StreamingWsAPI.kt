package com.moviles.streaming.core.network

import com.moviles.streaming.features.chat.data.models.StreamsResponse
import retrofit2.http.GET

interface StreamingWsAPI {
    @GET("streams/active/")
    suspend fun getActiveStreams(): StreamsResponse
}

