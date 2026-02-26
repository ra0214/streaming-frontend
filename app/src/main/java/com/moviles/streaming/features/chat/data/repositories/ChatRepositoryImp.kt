package com.moviles.streaming.features.chat.data.repositories

import com.google.gson.Gson
import com.moviles.streaming.core.di.ChatOkHttpClient
import com.moviles.streaming.core.di.WebSocketBaseUrl
import com.moviles.streaming.core.network.StreamingAPI
import com.moviles.streaming.features.chat.data.mapper.toDomain
import com.moviles.streaming.features.chat.data.models.ChatMessageDto
import com.moviles.streaming.features.chat.data.models.ChatSendDto
import com.moviles.streaming.features.chat.domain.entities.ChatMessage
import com.moviles.streaming.features.chat.domain.entities.Stream
import com.moviles.streaming.features.chat.domain.repositories.ChatRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class ChatRepositoryImp @Inject constructor(
    private val streamingWsAPI: StreamingAPI,
    @ChatOkHttpClient private val okHttpClient: OkHttpClient,
    @WebSocketBaseUrl private val baseWsUrl: String
) : ChatRepository {

    private val gson = Gson()
    private var webSocket: WebSocket? = null

    override suspend fun getActiveStreams(): List<Stream> {
        return streamingWsAPI.getActiveStreams().streams.map { it.toDomain() }
    }

    override fun startStream(streamerId: Int): Flow<ChatMessage> = callbackFlow {
        val url = "${baseWsUrl}stream/${streamerId}"
        val request = Request.Builder().url(url).build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {}

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val dto = gson.fromJson(text, ChatMessageDto::class.java)
                    trySend(dto.toDomain())
                } catch (e: Exception) {}
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                close()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                close(t)
            }
        }

        webSocket = okHttpClient.newWebSocket(request, listener)

        awaitClose {
            webSocket?.close(1000, "Stream terminado")
            webSocket = null
        }
    }

    override fun connectToStream(streamerId: Int, viewerId: Int): Flow<ChatMessage> = callbackFlow {
        val url = "${baseWsUrl}watch/${streamerId}/${viewerId}"
        val request = Request.Builder().url(url).build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {}

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val dto = gson.fromJson(text, ChatMessageDto::class.java)
                    trySend(dto.toDomain())
                } catch (e: Exception) {}
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                close()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                close(t)
            }
        }

        webSocket = okHttpClient.newWebSocket(request, listener)

        awaitClose {
            webSocket?.close(1000, "Desconexión del usuario")
            webSocket = null
        }
    }

    override fun sendMessage(message: String) {
        webSocket?.send(gson.toJson(ChatSendDto(content = message)))
    }

    override fun disconnect() {
        webSocket?.close(1000, "Desconexión del usuario")
        webSocket = null
    }
}
