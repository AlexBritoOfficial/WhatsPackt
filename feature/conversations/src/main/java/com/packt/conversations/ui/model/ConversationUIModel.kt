package com.packt.conversations.ui.model

data class ConversationUIModel(
    val id: String,
    val title: String?,
    val otherParticipantName: String,
    val otherParticipantAvatar: String,
    val lastMessage: String,
    val formattedTime: String // formatted version of lastMessageTimestamp
)
