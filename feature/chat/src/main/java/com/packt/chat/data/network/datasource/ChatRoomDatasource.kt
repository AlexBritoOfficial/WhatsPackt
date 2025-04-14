package com.packt.chat.data.network.datasource

import android.util.Log
import com.google.firebase.firestore.Query
import com.packt.chat.data.network.model.ChatRoomModel
import com.packt.chat.data.network.model.FireStoreMessageModel
import com.packt.chat.di.ChatModule.Companion.API_CHAT_ROOM_URL_NAME
import com.packt.chat.di.ChatModule.Companion.API_CLIENT
import com.packt.chat.domain.models.Message
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Named

class ChatRoomDataSource @Inject constructor(
    @Named(API_CLIENT) private val client: HttpClient,
    @Named(API_CHAT_ROOM_URL_NAME) private val url: String
) {
    suspend fun getInitialChatRoom(id: String): ChatRoomModel {
        return client.get(url.format(id)).body()
    }
}