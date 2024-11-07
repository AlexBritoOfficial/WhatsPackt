package com.packt.chat.data.model

import com.packt.chat.domain.models.Message
import kotlinx.serialization.Serializable

@Serializable
class WebsocketMessageModel(
    val id: String,
    val message: String,
    val senderName: String,
    val senderAvatar: String,
    val timestamp: String,
    val isMine: Boolean,
    val messageType: String,
    val messageDescription: String
) {
    companion object {
        const val TYPE_TEXT = "TEXT"
        const val TYPE_IMAGE = "IMAGE"

        fun fromDomain(message: Message): WebsocketMessageModel {
            return WebsocketMessageModel(
                id = message.id,
                senderName = message.senderName,
                senderAvatar = message.senderAvatar,
                timestamp = message.timestamp,
                isMine = message.isMine,
                messageType = message.fromContentType(),
                message = message.content,
                messageDescription = message.contentDescription
            )
        }
    }

    fun toDomain(): Message {
        return Message(
            id = id,
            senderName = senderName,
            senderAvatar = senderAvatar,
            timestamp = timestamp,
            isMine = isMine,
            contentType = toContentType(),
            content = message,
            contentDescription = messageDescription,
        )
    }

    fun toContentType(): Message.ContentType {
        return when (messageType) {
            TYPE_IMAGE -> Message.ContentType.IMAGE
            else -> Message.ContentType.TEXT
        }
    }
}

fun Message.fromContentType(): String {
    return when (contentType) {
      Message.ContentType.IMAGE -> WebsocketMessageModel.TYPE_IMAGE
        else -> WebsocketMessageModel.TYPE_TEXT
    }
}