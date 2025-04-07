package com.packt.chat.domain.usecases

import com.packt.chat.domain.IChatRoomRepository
import com.packt.chat.domain.models.ChatRoom
import javax.inject.Inject


class GetInitialChatRoomInformation @Inject constructor(private val iChatRoomRepository: IChatRoomRepository) {

    suspend operator fun invoke(chatId: String): ChatRoom {
        return iChatRoomRepository.getInitialChatRoom(chatId)
    }
}