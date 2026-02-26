package com.moviles.streaming.features.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.streaming.features.chat.domain.entities.ChatMessage
import com.moviles.streaming.features.chat.domain.usecases.ConnectToChatUseCase
import com.moviles.streaming.features.chat.domain.usecases.DisconnectChatUseCase
import com.moviles.streaming.features.chat.domain.usecases.SendChatMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val connectToChatUseCase: ConnectToChatUseCase,
    private val sendChatMessageUseCase: SendChatMessageUseCase,
    private val disconnectChatUseCase: DisconnectChatUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    fun connect(streamerId: Int, viewerId: Int) {
        viewModelScope.launch {
            _isConnected.value = true
            connectToChatUseCase(streamerId, viewerId).collect { message ->
                _messages.value = _messages.value + message
            }
            _isConnected.value = false
        }
    }

    fun sendMessage(text: String) {
        if (text.isNotBlank()) {
            sendChatMessageUseCase(text)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectChatUseCase()
    }
}

