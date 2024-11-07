package com.packt.chat.data.network.datasource.repository

import com.packt.chat.data.network.datasource.MessagesSocketDataSource
import com.packt.chat.domain.models.IMessageRepository
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepository @Inject constructor(private val dataSource: MessagesSocketDataSource): IMessageRepository {

    override suspend fun getMessages(chatId: String, userId: String): Flow<Message> {
       return dataSource.connect()
    }

    override suspend fun sendMessage(chatId: String, message: Message) {
        return dataSource.sendMessage(message)
    }

    override suspend fun disconnect(){
        dataSource.disconnect()
    }
}