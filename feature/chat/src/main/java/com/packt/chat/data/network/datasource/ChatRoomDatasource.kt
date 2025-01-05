package com.packt.chat.data.network.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class ChatRoomDatasource @Inject constructor(
    private val httpClient: HttpClient,
    private val url: String
) {

//    suspend fun getInitialChatRoom(chatId: String): ChatRoomModel{
//        return httpClient.get(url.format(chatId)).body()
//    }
}