package com.packt.chat.data.network.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import com.packt.chat.domain.models.Message
import java.text.SimpleDateFormat
import java.util.Locale

data class FireStoreMessageModel(
    @Transient
    val id: String,
    @get: PropertyName("senderId")
    @set: PropertyName("senderId")
    var senderId: String = "",
    @get: PropertyName("senderName")
    @set: PropertyName("senderName")
    var senderName: String = "",
    @get: PropertyName("senderAvatar")
    @set: PropertyName("SenderAvatar")
    var senderAvatar: String = "",
    @get: PropertyName("content")
    @set: PropertyName("content")
    var content: String = "",
    @get: PropertyName("timestamp")
    @set: PropertyName("timestamp")
    var timestamp : Timestamp = Timestamp.now()
) {

    // Converts the Message from the Domain layer into a FireStoreMessageModel
    companion object{
        fun fromDomain(message: Message): FireStoreMessageModel{
            return FireStoreMessageModel(
                id = "",
                senderName = message.senderName,
                senderAvatar = message.senderAvatar,
                content = message.content)
        }
    }

    // Converts the FireStoreMessageModel into a Message object within the domain layer
    fun toDomain(userId: String): Message {
        return Message(
            id = id,
            senderName = senderName,
            senderAvatar = senderAvatar,
            isMine = userId == senderId,
            contentType = Message.ContentType.TEXT,
            content = content,
            contentDescription = "",
            timestamp = timestamp.toDateString(),
        )
    }

    private fun Timestamp.toDateString(): String{

        // Create a SimpleDateFormat instance with the desired format and default Locale
        val dateFormatter = SimpleDateFormat("dd/mm/yyyy HH:mm:ss", Locale.getDefault())

        // Convert the Timestamp to a Date object
        val date = toDate()

        // Format the Date object into a string using the SimpleDateFormat
        return dateFormatter.format(date)

    }
}