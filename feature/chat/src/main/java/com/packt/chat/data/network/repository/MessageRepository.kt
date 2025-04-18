package com.packt.chat.data.network.datasource.repository

import com.packt.chat.data.network.datasource.FireStoreMessagesDataSource
import com.packt.chat.data.network.datasource.MessagesLocalDataSource
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.models.IMessageRepository
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MessageRepository @Inject constructor(//private val dataSource: MessagesSocketDataSource,
    private val remoteDataSource: FireStoreMessagesDataSource,
    private val localDataSource: MessagesLocalDataSource): IMessageRepository {

    override suspend fun observeMessages(userId: String, chatId: String): Flow<Message> {
        return remoteDataSource.observeMessages(userId = userId, chatId = chatId)
    }

    override suspend fun getInitialChatRoomInformation(userId: String, chatId: String): ChatRoom {
      return remoteDataSource.getInitialChatRoomInformation(userId = userId, chatId = chatId).first()
    }

    override suspend fun sendMessage(chatId: String, message: Message) {
        remoteDataSource.sendMessage(chatId = chatId, message = message)
    }

    override suspend fun disconnect() {
    }

    fun getLocalMessagesInConversation(conversation_id: Int): Flow<List<com.packt.data.database.Message>>{
        return localDataSource.getLocalMessagesInConversation(conversation_id =  conversation_id)
    }

    suspend fun insertMessageLocally(message: com.packt.data.database.Message): Long {
        return localDataSource.insertMessageLocally(message = message)
    }

    suspend fun deleteMessageLocally(message: com.packt.data.database.Message){
        return localDataSource.deleteMessageLocally(message = message)
    }

    suspend fun insertConversationLocally(conversation: com.packt.data.database.Conversation){
        return localDataSource.insertConversationLocally(conversation = conversation)
    }

}