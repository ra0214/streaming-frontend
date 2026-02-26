package com.moviles.streaming.features.chat.data.repositories

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.moviles.streaming.core.di.ChatOkHttpClient
import com.moviles.streaming.core.di.WebSocketBaseUrl
import com.moviles.streaming.core.network.StreamingAPI
import com.moviles.streaming.features.chat.data.dataresources.remote.mapper.toDomain
import com.moviles.streaming.features.chat.data.dataresources.remote.model.ChatMessageDto
import com.moviles.streaming.features.chat.data.dataresources.remote.model.ChatSendDto
import com.moviles.streaming.features.chat.data.dataresources.remote.model.MessageContentDto
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
import java.lang.reflect.Type
import javax.inject.Inject

class ChatRepositoryImp @Inject constructor(
    private val streamingWsAPI: StreamingAPI,
    @ChatOkHttpClient private val okHttpClient: OkHttpClient,
    @WebSocketBaseUrl private val baseWsUrl: String
) : ChatRepository {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(
            MessageContentDto::class.java,
            object : JsonDeserializer<MessageContentDto> {
                override fun deserialize(
                    json: JsonElement,
                    typeOfT: Type,
                    context: JsonDeserializationContext
                ): MessageContentDto {
                    return if (json.isJsonObject) {
                        MessageContentDto(json.asJsonObject.get("content")?.asString ?: "")
                    } else {
                        MessageContentDto(json.asString)
                    }
                }
            }
        )
        .create()
    private var webSocket: WebSocket? = null

    override suspend fun getActiveStreams(): List<Stream> {
        return try {
            val response = streamingWsAPI.getActiveStreams()
            response.streams.map { it.toDomain() }
        } catch (_: Exception) {
            emptyList()
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
                } catch (_: Exception) {}
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
