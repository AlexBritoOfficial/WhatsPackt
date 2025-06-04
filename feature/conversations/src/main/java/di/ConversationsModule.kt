package di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.repository.ConversationsRepository
import domain.IConversationsRepository
import javax.inject.Singleton

object ConversationsModule {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class RepositoryModule {

        @Binds
        abstract fun bindConversationsRepository(
            impl: ConversationsRepository
        ): IConversationsRepository
    }
}