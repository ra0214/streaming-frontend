package com.moviles.streaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.moviles.streaming.features.chat.presentation.ROUTE_STREAM_LIST
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
