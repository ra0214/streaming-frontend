package com.moviles.streaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.moviles.streaming.features.auth.presentation.viewmodel.LoginViewModel
import com.moviles.streaming.features.auth.presentation.viewmodel.RegisterViewModel
import com.moviles.streaming.features.auth.presentation.screens.LoginScreen
import com.moviles.streaming.features.auth.presentation.screens.RegisterScreen
import com.moviles.streaming.features.chat.presentation.chatGraph
import com.moviles.streaming.ui.theme.StreamingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StreamingTheme {
                val navController = rememberNavController()
                var currentScreen by rememberSaveable { mutableStateOf("login") }

                when (currentScreen) {
                    "login" -> {
                        val loginViewModel: LoginViewModel = hiltViewModel()
                        LoginScreen(
                            viewModel = loginViewModel,
                            onLoginSuccess = {
                                // Al tener éxito, pasamos al flujo de navegación de chat
                                currentScreen = "main_flow"
                            },
                            onNavigateToRegister = {
                                currentScreen = "registro"
                            }
                        )
                    }
                    "registro" -> {
                        val registerViewModel: RegisterViewModel = hiltViewModel()
                        RegisterScreen(
                            viewModel = registerViewModel,
                            onRegisterSuccess = {
                                currentScreen = "login"
                            },
                            onBackToLogin = {
                                currentScreen = "login"
                            }
                        )
                    }
                    "main_flow" -> {
                        // Aquí usamos el NavHost de tu compañero para el chat
                        NavHost(
                            navController = navController,
                            startDestination = "stream_list/1"
                        ) {
                            chatGraph(navController)
                        }
                    }
                }
            }
        }
    }
}
