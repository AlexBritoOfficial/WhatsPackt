package com.packt.chat.di

import com.packt.chat.data.WebsocketClient
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
        const val WEBSOCKET_URL = "ws://whatspackt.com/chat/%s"
        const val WEBSOCKET_CLIENT = "WEBSOCKET_CLIENT"
        const val WEBSOCKET_URL_NAME = "WEBSOCKET_URL"
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

}