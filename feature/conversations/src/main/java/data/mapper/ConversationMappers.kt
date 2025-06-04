// File: data/mappers/ConversationMappers.kt

package data.mappers

import data.network.dto.FirestoreConversationModel
import domain.Conversation
import domain.GroupConversation
import domain.PrivateConversation

fun mapToConversation(docId: String, firestoreConversationModel: FirestoreConversationModel): Conversation {
    return when (firestoreConversationModel.chatType) {
        "group" -> GroupConversation(
            id = docId,
            participants = firestoreConversationModel.participants,
            lastMessage = firestoreConversationModel.lastMessage,
            lastMessageTimestamp = firestoreConversationModel.lastMessageTimestamp!!,
            createdAt = firestoreConversationModel.createdAt,
            title = firestoreConversationModel.title ?: "Unnamed Group",
            profileImageUrl = firestoreConversationModel.profileImageUrl
        )
        else -> PrivateConversation(
            id = docId,
            participants = firestoreConversationModel.participants,
            lastMessage = firestoreConversationModel.lastMessage,
            lastMessageTimestamp = firestoreConversationModel.lastMessageTimestamp,
            createdAt = firestoreConversationModel.createdAt
        )
    }
}
