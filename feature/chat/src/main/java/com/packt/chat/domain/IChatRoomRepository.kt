package com.packt.chat.domain

import com.packt.chat.data.network.model.ChatRoomModel
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface IChatRoomRepository {
    suspend fun getInitialChatRoom(chatId: String): Flow<ChatRoomModel>
}