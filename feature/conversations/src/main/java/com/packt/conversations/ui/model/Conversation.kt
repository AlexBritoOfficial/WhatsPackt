package com.packt.conversations.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class Conversation(
    val id: String,
    val name: String,
    val message: String,
    val timestamp: String,
    val unreadCount: Int = 0,
)
