package com.packt.conversations.ui.mapper


import com.packt.conversations.ui.model.ConversationUIModel
import data.network.dto.FirestoreConversationModel
import java.text.SimpleDateFormat
import java.util.Locale

fun FirestoreConversationModel.toUiModel(): ConversationUIModel {
    val formattedTime = lastMessageTimestamp?.toDate()?.let {
        SimpleDateFormat("h:mm a", Locale.getDefault()).format(it)
    } ?: ""

    return ConversationUIModel(
        id = id,
        title = title,
        otherParticipantName = otherParticipantName,
        otherParticipantAvatar = otherParticipantAvatar,
        lastMessage = lastMessage,
        formattedTime = formattedTime
    )
}
