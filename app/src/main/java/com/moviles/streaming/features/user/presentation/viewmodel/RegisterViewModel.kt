package com.moviles.streaming.features.user.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.streaming.features.user.domain.usecases.UserRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUIState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRegisterUseCase: UserRegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState: StateFlow<RegisterUIState> = _uiState.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _rol = MutableStateFlow("follower")
    val rol: StateFlow<String> = _rol.asStateFlow()

    fun onUsernameChange(username: String) {
        _username.value = username
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onRolChange(rol: String) {
        _rol.value = rol
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val response = userRegisterUseCase(
                    username = _username.value,
                    rol = _rol.value,
                    password = _password.value
                )

                if (response.username.isNotEmpty()) {
                    _uiState.update { it.copy(isSuccess = true, isLoading = false) }
                } else {
                    _uiState.update {
                        it.copy(
                            error = "Error en el registro. Inténtalo de nuevo.",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }
}
