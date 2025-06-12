package com.packt.chat.domain.models

import com.google.firebase.Timestamp
import com.packt.chat.data.network.model.FireStoreMessageModel

data class Message(
    val id: String? = null, // This maps to senderId
    val timestamp: String? = null,
    val isMine: Boolean = false,
    val contentType: ContentType = ContentType.TEXT,
    val content: String = "",
    val contentDescription: String = ""
) {

    enum class ContentType {
        TEXT, IMAGE, VIDEO
    }

    fun toFireStoreMessageModel(): FireStoreMessageModel {
        return FireStoreMessageModel(
            senderId = id ?: "",
            content = content,
            messageType = contentType.name.lowercase(),
            timestamp = Timestamp.now()
        )
    }
}
