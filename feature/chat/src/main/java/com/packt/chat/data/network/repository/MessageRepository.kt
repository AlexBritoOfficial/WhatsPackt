package com.packt.chat.data.network.datasource.repository

import com.packt.chat.data.network.datasource.FireStoreMessagesDataSource
import com.packt.chat.data.network.datasource.MessagesSocketDataSource
import com.packt.chat.domain.models.IMessageRepository
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepository @Inject constructor(//private val dataSource: MessagesSocketDataSource,
                                            private val dataSource: FireStoreMessagesDataSource): IMessageRepository {

    override suspend fun getMessages(chatId: String): Flow<Message> {
        return dataSource.getMessages(chatId = chatId)
    }

    override suspend fun sendMessage(chatId: String, message: Message) {
        dataSource.sendMessage(chatId = chatId, message = message)
    }

    override suspend fun disconnect() {
    }

}