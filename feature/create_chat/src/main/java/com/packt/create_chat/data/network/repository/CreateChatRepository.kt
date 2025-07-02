package com.packt.create_chat.data.network.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.packt.create_chat.data.network.FirestoreUsersRemoteDataSource
import com.packt.create_chat.domain.CreateChatRepository
import kotlinx.coroutines.tasks.await
import java.util.UUID

class CreateChatRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val usersDataSource: FirestoreUsersRemoteDataSource
) : CreateChatRepository {

    override suspend fun checkUserExists(username: String): Boolean {
        return usersDataSource.checkUserExists(username)
    }

    override suspend fun createChat(
        currentUserId: String,
        participantId: String,
        otherParticipantName: String,
    ): String {

        return usersDataSource.createChatDocument(
            currentUserId = currentUserId,
            participantId = participantId,
            otherParticipantName = otherParticipantName
        )
    }

    override suspend fun getParticipantId(username: String): String {
        return usersDataSource.getParticipantId(username)
    }
}