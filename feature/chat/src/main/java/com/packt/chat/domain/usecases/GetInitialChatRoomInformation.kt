package com.packt.chat.domain.usecases

import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.models.IMessageRepository
import javax.inject.Inject


class GetInitialChatRoomInformation @Inject constructor(private val iMessageRepository: IMessageRepository) {

    suspend operator fun invoke(userId: String, chatId: String): ChatRoom {
        return iMessageRepository.getInitialChatRoomInformation(userId = userId, chatId = chatId)
    }
}