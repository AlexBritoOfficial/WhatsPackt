package com.packt.chat.domain.usecases

import com.packt.chat.domain.models.IMessageRepository
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMessages @Inject constructor(private val repository: IMessageRepository) {

    suspend operator fun invoke(userId: String, chatId: String): Flow<Message>{
        return repository.observeMessages(userId = userId, chatId = chatId)
    }

}