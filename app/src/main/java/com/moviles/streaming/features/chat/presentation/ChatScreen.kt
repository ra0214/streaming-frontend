package com.moviles.streaming.features.chat.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moviles.streaming.features.chat.domain.entities.ChatMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    streamerId: Int,
    viewerId: Int,
    onBack: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val inputText by viewModel.inputText.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(streamerId, viewerId) {
        viewModel.connect(streamerId, viewerId)
    }

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stream #$streamerId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            Column {
                if (uiState.error != null) {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = inputText,
                        onValueChange = { viewModel.onInputChange(it) },
                        placeholder = { Text("Escribe un mensaje...") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { viewModel.sendMessage() }) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(uiState.messages) { message ->
                ChatMessageItem(message)
            }
        }
    }
}

@Composable
private fun ChatMessageItem(message: ChatMessage) {
    when (message.type) {
        "system" -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
        else -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (message.sender != null) {
                    Text(
                        text = message.sender,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = message.message,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
