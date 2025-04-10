package com.packt.chat.di

import android.content.Context
import com.packt.chat.data.RestClient
import com.packt.chat.data.WebsocketClient
import com.packt.chat.data.network.datasource.FirebaseFirestoreProvider
import com.packt.chat.data.network.datasource.repository.MessageRepository
import com.packt.chat.data.network.repository.ChatRoomRepository
import com.packt.chat.domain.IChatRoomRepository
import com.packt.chat.domain.models.IMessageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ChatModule {

    companion object{
        const val WEBSOCKET_URL = "ws://localhost:8080/echo"
        const val WEBSOCKET_CLIENT = "WEBSOCKET_CLIENT"
        const val WEBSOCKET_URL_NAME = "WEBSOCKET_URL"
        const val API_CHAT_ROOM_URL = "https://firestore.googleapis.com/v1/projects/whatspackt-14428/databases/(default)/documents/"
        const val API_CHAT_ROOM_URL_NAME = "API_CHATROOM_URL"
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


    @Provides
    @Named(API_CHAT_ROOM_URL_NAME)
    fun providesApiChatUrl(): String {
        return API_CHAT_ROOM_URL
    }

    @Provides
    @Singleton
    fun providesFirebaseFirestoreProvider(@ApplicationContext context: Context): FirebaseFirestoreProvider {
        return FirebaseFirestoreProvider(context = context)
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