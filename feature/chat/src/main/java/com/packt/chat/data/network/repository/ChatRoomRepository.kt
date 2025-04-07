package com.packt.chat.data.network.repository

import com.packt.chat.data.network.datasource.ChatRoomDatasource
import com.packt.chat.domain.IChatRoomRepository
import com.packt.chat.domain.models.ChatRoom
import javax.inject.Inject

class ChatRoomRepository @Inject constructor(private val chatRoomDatasource: ChatRoomDatasource) : IChatRoomRepository {

    override suspend fun getInitialChatRoom(chatId: String): ChatRoom {
        val chatRoomApiModel = chatRoomDatasource.getInitialChatRoom(chatId)
        return chatRoomApiModel.toDomain()
    }


}