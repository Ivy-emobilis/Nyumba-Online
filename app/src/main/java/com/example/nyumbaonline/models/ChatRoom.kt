package com.example.nyumbaonline.models

data class ChatRoom(
    val id: String,
    val name: String,
    val description: String = "",
    val participants: List<String> = emptyList(),
    val lastMessage: String = "",
    val lastMessageTimestamp: Long = 0L,
    val unreadCount: Int = 0,
    val propertyId: String = "", // New field for property association
    val managementId: String = "" // New field for management association
)