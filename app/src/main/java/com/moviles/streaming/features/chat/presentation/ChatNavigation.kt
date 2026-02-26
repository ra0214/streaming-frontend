package com.moviles.streaming.features.chat.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ROUTE_STREAM_LIST = "stream_list/{viewerId}/{isStreamer}"
const val ROUTE_CHAT = "chat/{streamerId}/{viewerId}"

fun NavHostController.navigateToStreamList(viewerId: Int, isStreamer: Boolean) {
    navigate("stream_list/$viewerId/$isStreamer")
}

fun NavHostController.navigateToChat(streamerId: Int, viewerId: Int) {
    navigate("chat/$streamerId/$viewerId")
}

fun NavGraphBuilder.chatGraph(navController: NavHostController) {
    composable(
        route = ROUTE_STREAM_LIST,
        arguments = listOf(
            navArgument("viewerId") { type = NavType.IntType },
            navArgument("isStreamer") { type = NavType.BoolType }
        )
    ) { backStackEntry ->
        val viewerId = backStackEntry.arguments?.getInt("viewerId") ?: 0
        val isStreamer = backStackEntry.arguments?.getBoolean("isStreamer") ?: false
        StreamListScreen(
            viewerId = viewerId,
            isStreamer = isStreamer,
            onStreamClick = { streamerId, vid ->
                navController.navigateToChat(streamerId, vid)
            },
            onStartStream = { streamerId ->
                navController.navigateToChat(streamerId, viewerId)
            }
        )
    }

    composable(
        route = ROUTE_CHAT,
        arguments = listOf(
            navArgument("streamerId") { type = NavType.IntType },
            navArgument("viewerId") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val streamerId = backStackEntry.arguments?.getInt("streamerId") ?: 0
        val viewerId = backStackEntry.arguments?.getInt("viewerId") ?: 0
        ChatScreen(
            streamerId = streamerId,
            viewerId = viewerId,
            onBack = { navController.popBackStack() }
        )
    }
}

