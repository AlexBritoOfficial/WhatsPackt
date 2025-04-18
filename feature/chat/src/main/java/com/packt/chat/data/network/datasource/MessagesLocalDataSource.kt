package com.packt.chat.data.network.datasource

import com.packt.data.database.Conversation
import com.packt.data.database.ConversationDao
import com.packt.data.database.Message
import com.packt.data.database.MessageDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessagesLocalDataSource @Inject constructor(
    private val messageDao: MessageDao,
    private val conversationDao: ConversationDao
) {

    fun getLocalMessagesInConversation(conversation_id: Int): Flow<List<Message>> {
        return messageDao.getMessages(conversation_id = conversation_id)
    }

    suspend fun insertMessageLocally(message: Message): Long {
        return messageDao.insertMessage(message = message)
    }

    suspend fun deleteMessageLocally(message: Message) {
        return messageDao.deleteMessage(message = message)
    }

    suspend fun insertConversationLocally(conversation: Conversation) {
        conversationDao.insertConversation(conversation = conversation)
    }
}