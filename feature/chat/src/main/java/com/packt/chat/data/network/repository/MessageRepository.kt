package com.packt.chat.data.network.datasource.repository

import com.packt.chat.data.network.datasource.FireStoreMessagesDataSource
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.models.IMessageRepository
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MessageRepository @Inject constructor(//private val dataSource: MessagesSocketDataSource,
                                            private val dataSource: FireStoreMessagesDataSource): IMessageRepository {

    override suspend fun observeMessages(userId: String, chatId: String): Flow<Message> {
        return dataSource.observeMessages(userId = userId, chatId = chatId)
    }

    override suspend fun getInitialChatRoomInformation(userId: String, chatId: String): ChatRoom {
      return dataSource.getInitialChatRoomInformation(userId = userId, chatId = chatId).first()
    }

    override suspend fun sendMessage(chatId: String, message: Message) {
        dataSource.sendMessage(chatId = chatId, message = message)
    }

    override suspend fun disconnect() {
    }

}