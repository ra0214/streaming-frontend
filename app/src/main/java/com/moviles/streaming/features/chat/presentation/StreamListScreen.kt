package com.moviles.streaming.features.chat.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moviles.streaming.features.chat.domain.entities.Stream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StreamListScreen(
    viewerId: Int,
    onStreamClick: (streamerId: Int, viewerId: Int) -> Unit,
    viewModel: StreamListViewModel = hiltViewModel()
) {
    val streams by viewModel.streams.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Streams activos") },
                actions = {
                    IconButton(onClick = { viewModel.loadStreams() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Recargar")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                error != null -> {
                    Text(
                        text = error!!,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }
                streams.isEmpty() -> {
                    Text(
                        text = "No hay streams activos",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(streams) { stream ->
                            StreamItem(
                                stream = stream,
                                onClick = { onStreamClick(stream.streamerId, viewerId) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StreamItem(stream: Stream, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = stream.username, style = MaterialTheme.typography.titleMedium)
                Text(text = "Streamer ID: ${stream.streamerId}", style = MaterialTheme.typography.bodySmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${stream.viewersCount}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

