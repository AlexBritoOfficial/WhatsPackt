package com.packt.chat.domain.models

data class ChatRoom(
    val id: String,
    val senderName: String,
    val senderAvatar: String,
    val messages: List<Message>
)