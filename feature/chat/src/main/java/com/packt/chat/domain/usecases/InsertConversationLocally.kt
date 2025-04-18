package com.packt.chat.domain.usecases

import com.packt.chat.data.network.datasource.repository.MessageRepository
import com.packt.data.database.Conversation
import com.packt.data.database.ConversationDao
import javax.inject.Inject

class InsertConversationLocally @Inject constructor(private val messageRepository: MessageRepository) {

    suspend operator fun invoke(conversation: Conversation) {
        return messageRepository.insertConversationLocally(conversation = conversation)
    }
}
