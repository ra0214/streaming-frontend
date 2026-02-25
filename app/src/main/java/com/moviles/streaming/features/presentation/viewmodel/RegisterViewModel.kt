package com.moviles.streaming.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RegisterUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(userName: String, email: String, password: String) {
        // Simulación de registro sin funcionalidad real de red
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        // Simplemente marcamos como éxito directamente para que la vista pueda navegar
        _uiState.update { it.copy(isLoading = false, isSuccess = true) }
    }

    fun resetState() {
        _uiState.update { RegisterUiState() }
    }
}
