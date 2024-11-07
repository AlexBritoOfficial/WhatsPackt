package com.packt.chat.domain.usecases

import com.packt.chat.domain.models.IMessageRepository
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveMessages @Inject constructor(private val repository: IMessageRepository) {

    suspend operator fun invoke(chatId: String, userId: String): Flow<Message>{
        return repository.getMessages(chatId = chatId, userId = userId)
    }

}