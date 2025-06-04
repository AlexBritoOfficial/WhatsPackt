package domain

import data.network.dto.FirestoreConversationModel

interface IConversationsRepository {

    suspend fun getConversations(userId: String): List<FirestoreConversationModel>
}