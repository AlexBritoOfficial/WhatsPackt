package com.packt.chat.domain.models

import com.google.firebase.Timestamp
import com.packt.chat.data.network.model.FireStoreMessageModel

data class Message(
    val id: String? = null,
    val senderName: String = "",
    val senderAvatar: String = "",
    val timestamp: String? = null,
    val isMine: Boolean = false,
    val contentType: ContentType = ContentType.TEXT,
    val content: String = "",
    val contentDescription: String = ""
) {

    enum class ContentType {
        TEXT, IMAGE
    }

    fun toFireStoreMessageModel(): FireStoreMessageModel {
        return FireStoreMessageModel(
            id = id ?: "",
            senderName = senderName,
            senderAvatar = senderAvatar
        )
    }
}