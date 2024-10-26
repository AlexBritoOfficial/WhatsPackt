package com.packt.chat.data.network

import android.icu.util.Freezable
import com.packt.chat.ui.model.Message
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.converter
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.CloseReason
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class MessagesSocketDataSource @Inject constructor(private val httpClient: HttpClient){

    private lateinit var webSocketSession: DefaultClientWebSocketSession

//    suspend fun connect(url: String): Flow<Message> {
//        return httpClient.webSocketSession{url(url)}
//            .apply { webSocketSession = this }
//            .incoming
//            .receiveAsFlow()
//            .map { frame ->
//                webSocketSession.handleMessage(frame)
//            }.filterNotNull().map{
//                it.toDomain()
//            }
//
//    }

    suspend fun sendMessage(message: String){
        webSocketSession.send(Frame.Text(message))
    }

    suspend fun disconnect(){
        webSocketSession.close(CloseReason(CloseReason.Codes.NORMAL, "Disconnect"))
    }

//    private suspend fun DefaultClientWebSocketSession.handleMessage(frame: Frame): WebsocketMessageModel? {
//        return when (frame){
//            is Frame.Text -> converter?.deserialize(frame)
//            is Frame.Close -> {
//                disconnect()
//                null
//            }
//            else -> null
//        }
//    }

    class WebsocketMessageModel {

    }
}