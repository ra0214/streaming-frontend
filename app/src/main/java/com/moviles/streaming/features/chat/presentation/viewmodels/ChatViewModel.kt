package com.moviles.streaming.features.chat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.streaming.features.chat.domain.usecases.ConnectToChatUseCase
import com.moviles.streaming.features.chat.domain.usecases.DisconnectChatUseCase
import com.moviles.streaming.features.chat.domain.usecases.GetLocalChatMessagesUseCase
import com.moviles.streaming.features.chat.domain.usecases.SendChatMessageUseCase
import com.moviles.streaming.features.chat.presentation.screens.ChatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val connectToChatUseCase: ConnectToChatUseCase,
    private val sendChatMessageUseCase: SendChatMessageUseCase,
    private val disconnectChatUseCase: DisconnectChatUseCase,
    private val getLocalChatMessagesUseCase: GetLocalChatMessagesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    // SharedFlow para eventos únicos (errores, alertas) - no se repiten al recomponer
    private val _events = MutableSharedFlow<ChatEvent>()
    val events: SharedFlow<ChatEvent> = _events.asSharedFlow()

    private var currentStreamerId: Int = -1

    fun onInputChange(text: String) {
        _inputText.value = text
    }

    fun connect(streamerId: Int, viewerId: Int) {
        currentStreamerId = streamerId

        // SSOT: observar mensajes desde la BD local
        viewModelScope.launch {
            getLocalChatMessagesUseCase(streamerId).collect { localMessages ->
                _uiState.update { it.copy(messages = localMessages) }
            }
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isConnected = true, error = null) }
            try {
                connectToChatUseCase(streamerId, viewerId).collect { _ ->
                }
            } catch (e: Exception) {
                val errorMsg = "Error de conexión: ${e.localizedMessage}"
                _uiState.update { it.copy(isConnected = false, error = errorMsg) }
                _events.emit(ChatEvent.Error(errorMsg))
            } finally {
                _uiState.update { it.copy(isConnected = false) }
            }
        }
    }

    fun sendMessage() {
        val text = _inputText.value.trim()
        if (text.isBlank() || currentStreamerId == -1) return
        _inputText.value = ""
        viewModelScope.launch {
            try {
                sendChatMessageUseCase(text, currentStreamerId)
            } catch (e: Exception) {
                val errorMsg = "No se pudo enviar el mensaje"
                _uiState.update { it.copy(error = errorMsg) }
                _events.emit(ChatEvent.Error(errorMsg))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectChatUseCase()
    }
}

/** Eventos únicos emitidos via SharedFlow */
sealed class ChatEvent {
    data class Error(val message: String) : ChatEvent()
}
