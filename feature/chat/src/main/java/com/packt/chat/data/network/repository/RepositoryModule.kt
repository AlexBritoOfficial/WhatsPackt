package com.packt.chat.data.network.repository

import com.packt.chat.data.network.datasource.MessagesSocketDataSource
import com.packt.chat.data.network.datasource.repository.MessageRepository
import com.packt.chat.domain.models.IMessageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMessageRepository(messagesSocketDataSource: MessagesSocketDataSource): IMessageRepository {
        return MessageRepository(messagesSocketDataSource)
    }
}