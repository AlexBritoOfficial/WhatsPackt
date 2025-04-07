package com.packt.chat.data.network.datasource

import androidx.navigation.Navigator
import com.packt.chat.data.network.model.ChatRoomModel
import com.packt.chat.di.ChatModule.Companion.API_CHAT_ROOM_URL_NAME
import com.packt.chat.di.ChatModule.Companion.API_CLIENT
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Named

class ChatRoomDatasource @Inject constructor(
    @Named(API_CLIENT) private val httpClient: HttpClient,
    @Named (API_CHAT_ROOM_URL_NAME) private val url: String
) {

    suspend fun getInitialChatRoom(chatId: String): ChatRoomModel {
        return httpClient.get(url.format(chatId)).body()
    }
}