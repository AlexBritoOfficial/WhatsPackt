package com.packt.chat.domain

import com.packt.chat.domain.models.ChatRoom

interface IChatRoomRepository {
    suspend fun getInitialChatRoom(chatId: String): ChatRoom
}