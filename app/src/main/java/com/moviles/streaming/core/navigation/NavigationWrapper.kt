package com.moviles.streaming.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.moviles.streaming.features.user.presentation.screens.LoginScreen

import com.moviles.streaming.features.user.presentation.screens.RegisterScreen
import com.moviles.streaming.features.chat.presentation.screens.ChatScreen
import com.moviles.streaming.features.chat.presentation.screens.StreamListScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Login) {

        composable<Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(StreamList(viewerId = 1)) {
                        popUpTo(Login) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Register)
                }
            )
        }

        composable<Register> {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Login) {
                        popUpTo(Register) { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable<StreamList> {
            val route = it.toRoute<StreamList>()
            StreamListScreen(
                viewerId = route.viewerId,
                onStreamClick = { streamerId, viewerId ->
                    navController.navigate(Chat(streamerId = streamerId, viewerId = viewerId))
                }
            )
        }

        composable<Chat> {
            val route = it.toRoute<Chat>()
            ChatScreen(
                streamerId = route.streamerId,
                viewerId = route.viewerId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
