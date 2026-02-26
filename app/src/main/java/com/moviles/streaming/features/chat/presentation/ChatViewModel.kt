package com.moviles.streaming.features.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.streaming.features.chat.domain.usecases.ConnectToChatUseCase
import com.moviles.streaming.features.chat.domain.usecases.DisconnectChatUseCase
import com.moviles.streaming.features.chat.domain.usecases.SendChatMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val connectToChatUseCase: ConnectToChatUseCase,
    private val sendChatMessageUseCase: SendChatMessageUseCase,
    private val disconnectChatUseCase: DisconnectChatUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    fun onInputChange(text: String) {
        _inputText.value = text
    }

    fun connect(streamerId: Int, viewerId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isConnected = true, error = null) }
            try {
                connectToChatUseCase(streamerId, viewerId).collect { message ->
                    _uiState.update { it.copy(messages = it.messages + message) }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isConnected = false,
                        error = "Error de conexión: ${e.localizedMessage}"
                    )
                }
            } finally {
                _uiState.update { it.copy(isConnected = false) }
            }
        }
    }

    fun sendMessage() {
        val text = _inputText.value
        if (text.isNotBlank()) {
            sendChatMessageUseCase(text)
            _inputText.value = ""
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectChatUseCase()
    }
}
