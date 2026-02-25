package com.moviles.streaming.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(userName: String, password: String) {
        // Simulación de login sin funcionalidad real de red
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        // Simplemente marcamos como éxito directamente para que la vista pueda navegar
        _uiState.update { it.copy(isLoading = false, isSuccess = true) }
    }
    
    fun resetState() {
        _uiState.update { LoginUiState() }
    }
}
