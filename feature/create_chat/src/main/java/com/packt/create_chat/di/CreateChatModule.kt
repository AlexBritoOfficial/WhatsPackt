package com.packt.create_chat.di

import com.google.firebase.firestore.FirebaseFirestore
import com.packt.create_chat.data.network.FirestoreUsersRemoteDataSource
import com.packt.create_chat.data.network.repository.CreateChatRepositoryImpl
import com.packt.create_chat.domain.CreateChatRepository
import com.packt.create_chat.domain.usecases.CheckUserExistsUseCase
import com.packt.create_chat.domain.usecases.CreateChatUseCase
import com.packt.create_chat.domain.usecases.GetParticipantIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreateChatModule {


    @Provides
    @Singleton
    fun provideFirestoreUsersRemoteDataSource(
        firestore: FirebaseFirestore
    ): FirestoreUsersRemoteDataSource {
        return FirestoreUsersRemoteDataSource(firestore)
    }

    @Provides
    @Singleton
    fun provideCreateChatRepository(
        firestore: FirebaseFirestore,
        usersDataSource: FirestoreUsersRemoteDataSource
    ): CreateChatRepository {
        return CreateChatRepositoryImpl(firestore, usersDataSource)
    }

    @Provides
    fun provideCheckUserExistsUseCase(
        repository: CreateChatRepository
    ): CheckUserExistsUseCase {
        return CheckUserExistsUseCase(repository)
    }

    @Provides
    fun provideGetParticipantIdUseCase(
        repository: CreateChatRepository
    ): GetParticipantIdUseCase {
        return GetParticipantIdUseCase(repository)
    }

    @Provides
    fun provideCreateChatUseCase(
        repository: CreateChatRepository
    ): CreateChatUseCase {
        return CreateChatUseCase(repository)
    }
}