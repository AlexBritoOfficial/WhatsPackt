package com.packt.chat.ui.model

data class Message(
    val id: String,
    val senderName: String,
    val senderAvatar: String,
    val timestamp: String,
    val isMine: Boolean,
    val messageContent: MessageContent
)

sealed class MessageContent {
    data class TextMessage(val message: String) : MessageContent()

    data class ImageMessage(
        val imageUrl: String,
        val contentDescription: String
    ) : MessageContent()

    data class VideoMessage(
        val imageUrl: String,
        val contentDescription: String
    ) : MessageContent()
}
