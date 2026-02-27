package com.moviles.streaming.features.chat.data.dataresources.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val streamerId: Int,
    val type: String,
    val sender: String?,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isPending: Boolean = false
)

