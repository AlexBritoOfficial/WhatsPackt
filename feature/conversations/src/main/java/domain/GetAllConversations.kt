package domain

import data.network.dto.FirestoreConversationModel
import javax.inject.Inject

class GetAllConversations @Inject constructor(private val repository: IConversationsRepository) {

    suspend operator fun invoke(userId: String): List<FirestoreConversationModel> {
        return repository.getConversations(userId = userId)
    }

}