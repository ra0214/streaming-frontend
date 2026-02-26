package com.moviles.streaming.features.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.streaming.features.chat.domain.entities.Stream
import com.moviles.streaming.features.chat.domain.usecases.GetActiveStreamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreamListViewModel @Inject constructor(
    private val getActiveStreamsUseCase: GetActiveStreamsUseCase
) : ViewModel() {

    private val _streams = MutableStateFlow<List<Stream>>(emptyList())
    val streams: StateFlow<List<Stream>> = _streams

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadStreams()
    }

    fun loadStreams() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _streams.value = getActiveStreamsUseCase()
            } catch (e: Exception) {
                _error.value = "Error al cargar streams: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

