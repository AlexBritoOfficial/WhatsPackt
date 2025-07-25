package data.repository

import data.network.dto.FirestoreConversationModel
import data.network.datasource.remote.FireStoreConversationsRemoteDataSource
import domain.IConversationsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ConversationsRepository @Inject constructor(private val remoteDataSource: FireStoreConversationsRemoteDataSource) : IConversationsRepository{

    override suspend fun getConversations(userId: String): List<FirestoreConversationModel> {
        return remoteDataSource.getConversations(userId = userId).first()
    }
}