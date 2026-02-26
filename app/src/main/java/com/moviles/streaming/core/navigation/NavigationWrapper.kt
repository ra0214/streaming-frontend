package com.moviles.streaming.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.moviles.streaming.features.presentation.screens.LoginScreen
import com.moviles.streaming.features.presentation.screens.RegisterScreen
import com.moviles.streaming.features.chat.presentation.ChatScreen
import com.moviles.streaming.features.chat.presentation.StreamListScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Login
    ) {
        composable<Login> {
            LoginScreen(
                viewModel = hiltViewModel(),
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
                viewModel = hiltViewModel(),
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

        composable<StreamList> { backStackEntry ->
            val route = backStackEntry.toRoute<StreamList>()
            StreamListScreen(
                viewerId = route.viewerId,
                viewModel = hiltViewModel(),
                onStreamClick = { streamerId, viewerId ->
                    navController.navigate(Chat(streamerId = streamerId, viewerId = viewerId))
                }
            )
        }

        composable<Chat> { backStackEntry ->
            val route = backStackEntry.toRoute<Chat>()
            ChatScreen(
                streamerId = route.streamerId,
                viewerId = route.viewerId,
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }
    }
}
