package com.packt.chat.data.network.repository

import com.packt.chat.data.network.datasource.ChatRoomDatasource
import com.packt.chat.data.network.model.ChatRoomModel
import com.packt.chat.data.network.model.FireStoreMessageModel
import com.packt.chat.domain.IChatRoomRepository
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRoomRepository @Inject constructor(private val chatRoomDatasource: ChatRoomDatasource) : IChatRoomRepository {

    override suspend fun getInitialChatRoom(chatId: String): Flow<ChatRoomModel> {
        return chatRoomDatasource.getInitialChatRoom(chatId)
    }

  }