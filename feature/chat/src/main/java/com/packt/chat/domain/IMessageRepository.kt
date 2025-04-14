package com.packt.chat.domain.models

import kotlinx.coroutines.flow.Flow

interface IMessageRepository {

    suspend fun observeMessages(userId: String, chatId: String): Flow<Message>

    suspend fun getInitialChatRoomInformation(userId: String, chatId: String): ChatRoom

    suspend fun sendMessage(chatId: String, message: Message)

    suspend fun disconnect()

}