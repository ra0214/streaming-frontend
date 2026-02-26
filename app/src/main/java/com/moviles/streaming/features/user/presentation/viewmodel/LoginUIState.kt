package com.moviles.streaming.features.user.presentation.viewmodel

data class LoginUIState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)
