package com.packt.chat.di

import com.packt.chat.data.RestClient
import com.packt.chat.data.WebsocketClient
import com.packt.chat.data.network.datasource.repository.MessageRepository
import com.packt.chat.data.network.repository.ChatRoomRepository
import com.packt.chat.domain.IChatRoomRepository
import com.packt.chat.domain.models.IMessageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Named


@InstallIn(SingletonComponent::class)
@Module
class ChatModule {

    companion object{
        const val WEBSOCKET_URL = "ws://localhost:8080/echo"
        const val WEBSOCKET_CLIENT = "WEBSOCKET_CLIENT"
        const val WEBSOCKET_URL_NAME = "WEBSOCKET_URL"
        const val API_CHAT_ROOM_URL = "http://localhost:8080/chats/%s"
        const val API_CHAT_ROOM_URL_NAME = "CHATROOM_URL"
        const val API_CLIENT = "API_CLIENT"
    }

    @Provides
    @Named(WEBSOCKET_CLIENT)
    fun providesWebSocketClient(): HttpClient{
        return WebsocketClient.client
    }

    @Provides
    @Named(WEBSOCKET_URL_NAME)
    fun providesWebSocketURL(): String{
        return WEBSOCKET_URL
    }

    @Provides
    @Named(API_CLIENT)
    fun providesApiClient(): HttpClient{
        return RestClient.client
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class ChatBindingModule {

    @Binds
    abstract fun providesMessageRepository(messageRepository: MessageRepository): IMessageRepository

    @Binds
    abstract fun providesChatRoomRepository(messageRepository: ChatRoomRepository): IChatRoomRepository

}