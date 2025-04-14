package com.packt.chat.data.network.repository

import com.packt.chat.data.network.datasource.ChatRoomDataSource
import com.packt.chat.domain.IChatRoomRepository
import com.packt.chat.domain.models.ChatRoom
import javax.inject.Inject

class ChatRoomRepository @Inject constructor(
    private val dataSource: ChatRoomDataSource
): IChatRoomRepository {
    override suspend fun getInitialChatRoom(id: String): ChatRoom {
        val chatRoomApiModel = dataSource.getInitialChatRoom(id)
        return chatRoomApiModel.toDomain()
    }
}