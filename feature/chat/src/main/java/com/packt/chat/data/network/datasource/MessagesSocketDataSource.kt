package com.packt.chat.data.network.datasource

import android.util.Log
import com.packt.chat.data.model.WebsocketMessageModel
import com.packt.chat.di.ChatModule.Companion.WEBSOCKET_CLIENT
import com.packt.chat.di.ChatModule.Companion.WEBSOCKET_URL_NAME
import com.packt.chat.domain.models.Message
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.converter
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.serialization.deserialize
import io.ktor.serialization.serialize
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.io.IOException
import javax.inject.Inject
import javax.inject.Named

/**
 * What are the steps that are needed for us to create a WebSocket component in Android.
 *
 * 1) We need to create a HTTPClient component for us to manage the facilitation of the
 *    the socket
 *
 *
 * ***/

class MessagesSocketDataSource @Inject constructor(
    @Named(WEBSOCKET_CLIENT) private val httpClient: HttpClient,
    @Named(WEBSOCKET_URL_NAME) private val webSocketUrl: String
) {

    private lateinit var webSocketSession: DefaultClientWebSocketSession

    companion object {
        val TAG = "MessagesSocketDataSource"
        val MAX_TRIES = 5
        val RETRY_DELAY = 1000L
    }

    suspend fun connect(): Flow<Message> {

        return flow {

            try {
                httpClient.webSocketSession { url(webSocketUrl) }
                    .apply { webSocketSession = this }
                    .incoming
                    .receiveAsFlow()
                    .collect { frame ->
                        try {
                            // Handle errors while processing the message
                            val message = webSocketSession.handleMessage(frame)?.toDomain()
                            if (message != null) {
                                emit(message)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error handling WebSocket frame", e)
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Error connecting to WebSocket", e)
            }
        }.retryWhen { cause, attempt ->
            if (cause is IOException && attempt < MAX_TRIES) {
                delay(RETRY_DELAY)
                true
            } else {
                false
            }
        }.catch { e ->
            Log.e(TAG, "Error in Websocket Flow", e)
        }
    }

    suspend fun sendMessage(message: Message) {

        val webSocketMessage = WebsocketMessageModel.fromDomain(message)

        webSocketSession.converter?.serialize(webSocketMessage)?.let {
            webSocketSession.send(it)
        }
    }

    suspend fun disconnect() {
        webSocketSession.close(CloseReason(CloseReason.Codes.NORMAL, "Disconnect"))
    }

    /**
     * Takes the incoming Frame object that is coming through the socket and deserializes it into a WebSocketMessageModel object
     **/
    private suspend fun DefaultClientWebSocketSession.handleMessage(frame: Frame): WebsocketMessageModel? {
        return when (frame) {
            is Frame.Text -> converter?.deserialize(frame)
            is Frame.Close -> {
                disconnect()
                null
            }

            else -> null
        }
    }
}