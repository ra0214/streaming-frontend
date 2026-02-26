package com.moviles.streaming.features.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.streaming.features.chat.domain.usecases.GetActiveStreamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreamListViewModel @Inject constructor(
    private val getActiveStreamsUseCase: GetActiveStreamsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StreamListUiState())
    val uiState: StateFlow<StreamListUiState> = _uiState.asStateFlow()

    init {
        loadStreams()
    }

    fun loadStreams() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val streams = getActiveStreamsUseCase()
                _uiState.update { it.copy(streams = streams, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar streams: ${e.localizedMessage}"
                    )
                }
            }
        }
    }
}
