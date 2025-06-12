package com.packt.chat.data.network.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import com.packt.chat.domain.models.Message
import java.text.SimpleDateFormat
import java.util.*

data class FireStoreMessageModel(
    @Transient
    val id: String = "",

    @get: PropertyName("content")
    @set: PropertyName("content")
    var content: String = "",

    @get: PropertyName("senderId")
    @set: PropertyName("senderId")
    var senderId: String = "",

    @get: PropertyName("messageType")
    @set: PropertyName("messageType")
    var messageType: String = "text", // default to "text"

    @get: PropertyName("timestamp")
    @set: PropertyName("timestamp")
    var timestamp: Timestamp = Timestamp.now()
) {

    companion object {
        fun fromDomain(message: Message): FireStoreMessageModel {
            return FireStoreMessageModel(
                senderId = message.id ?: "",
                content = message.content,
                messageType = message.contentType.name.lowercase()
            )
        }
    }

    fun toMessageDomain(currentUserId: String): Message {
        return Message(
            id = senderId, // same as above
            isMine = currentUserId == senderId,
            contentType = when (messageType) {
                "text" -> Message.ContentType.TEXT
                "image" -> Message.ContentType.IMAGE
                "video" -> Message.ContentType.VIDEO
                else -> Message.ContentType.TEXT
            },
            content = content,
            contentDescription = "",
            timestamp = timestamp.toDateString()
        )
    }

    private fun Timestamp.toDateString(): String {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormatter.format(this.toDate())
    }

    fun FireStoreMessageModel.toDbMessage(chatId: String): com.packt.data.database.Message {
        return com.packt.data.database.Message(
            chatId = chatId,
            id = this.senderId,
            timestamp = this.timestamp.toDate().toString(),
            contentType = this.messageType.uppercase(),
            content = this.content,
            contentDescription = ""
        )
    }
}
