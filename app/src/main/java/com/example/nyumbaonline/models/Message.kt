package com.example.nyumbaonline.models

import java.util.UUID

// Place this in a file named Message.kt
data class Message(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val sender: String,
    val senderId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isFromCurrentUser: Boolean,
    val roomId: String
)