package com.packt.chat.data.network.model

import com.packt.chat.data.model.WebsocketMessageModel
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.models.Message
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomModel(
    val id: String,
    val senderName: String,
    val senderAvatar: String,
    val lastMessages: List<WebsocketMessageModel>
) {
    fun toDomain(): ChatRoom {
        return ChatRoom(
            id = id,
            senderName = senderName,
            senderAvatar = senderAvatar,
            lastMessages = lastMessages.map { it.toDomain() } // 👈 map each WebsocketMessageModel to domain.Message
        )
    }
}
