package com.packt.chat.domain.usecases

import com.packt.chat.domain.models.IMessageRepository
import com.packt.chat.domain.models.Message
import javax.inject.Inject

class SendMessage @Inject constructor(private val repository: IMessageRepository) {

    suspend operator fun invoke(chatId: String, message: Message){
        repository.sendMessage(chatId = chatId, message = message)
    }
}