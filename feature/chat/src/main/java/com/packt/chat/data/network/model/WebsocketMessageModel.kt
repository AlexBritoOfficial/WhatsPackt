package com.packt.chat.data.model

import com.packt.chat.domain.models.Message
import kotlinx.serialization.Serializable

@Serializable
class WebsocketMessageModel(
    val id: String?,
    val message: String,
    val senderName: String,
    val senderAvatar: String,
    val timestamp: String?,
    val isMine: Boolean,
    val messageType: String,
    val messageDescription: String
) {
    companion object {
        const val TYPE_TEXT = "TEXT"
        const val TYPE_IMAGE = "IMAGE"

        /**
         * Converts a domain [Message] into a [WebsocketMessageModel].
         * Since domain.Message doesn't contain senderName/senderAvatar, they must be passed separately.
         */
        fun fromDomain(
            message: Message,
            senderName: String,
            senderAvatar: String
        ): WebsocketMessageModel {
            return WebsocketMessageModel(
                id = message.id,
                senderName = senderName,
                senderAvatar = senderAvatar,
                timestamp = message.timestamp,
                isMine = message.isMine,
                messageType = message.fromContentType(),
                message = message.content,
                messageDescription = message.contentDescription
            )
        }
    }

    /**
     * Converts this WebSocket model into a domain-layer [Message].
     * senderName and senderAvatar are intentionally not passed into the domain model.
     */
    fun toDomain(): Message {
        return Message(
            id = id,
            timestamp = timestamp,
            isMine = isMine,
            contentType = toContentType(),
            content = message,
            contentDescription = messageDescription,
        )
    }

    private fun toContentType(): Message.ContentType {
        return when (messageType.uppercase()) {
            TYPE_IMAGE -> Message.ContentType.IMAGE
            else -> Message.ContentType.TEXT
        }
    }
}

// Extension function to convert contentType enum into string
fun Message.fromContentType(): String {
    return when (contentType) {
        Message.ContentType.IMAGE -> WebsocketMessageModel.TYPE_IMAGE
        else -> WebsocketMessageModel.TYPE_TEXT
    }
}
