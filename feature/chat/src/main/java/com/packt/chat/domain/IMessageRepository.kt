package com.packt.chat.domain.models

import kotlinx.coroutines.flow.Flow

interface IMessageRepository {

    suspend fun getMessages(chatId: String, userId: String): Flow<Message>

    suspend fun sendMessage(chatId: String, message: Message)

    suspend fun disconnect()
}