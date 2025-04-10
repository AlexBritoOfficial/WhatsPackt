package com.packt.chat.domain.usecases

import com.packt.chat.data.network.model.ChatRoomModel
import com.packt.chat.domain.IChatRoomRepository
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetInitialChatRoomInformation @Inject constructor(private val iChatRoomRepository: IChatRoomRepository) {

    suspend operator fun invoke(chatId: String): Flow<ChatRoomModel>{
        return iChatRoomRepository.getInitialChatRoom(chatId = chatId)
    }
}