package com.packt.create_chat.data.network

import com.google.firebase.firestore.FirebaseFirestore
import data.network.dto.FirestoreConversationModel
import data.network.dto.ParticipantMeta
import kotlinx.coroutines.tasks.await


class FirestoreUsersRemoteDataSource(
    private val firestore: FirebaseFirestore
) {

    suspend fun checkUserExists(username: String): Boolean {
        val snapshot = firestore
            .collection("users")
            .whereEqualTo("username", username)
            .get()
            .await()
        return !snapshot.isEmpty
    }

    suspend fun getParticipantId(username: String): String {
        val snapshot = firestore
            .collection("users")
            .whereEqualTo("username", username)
            .get()
            .await()

        return snapshot.documents.firstOrNull()?.id
            ?: throw Exception("User not found")
    }

    suspend fun createChatDocument(
        currentUserId: String,
        participantId: String,
        otherParticipantName: String,
    ): String {
        val docRef = firestore.collection("chats").document() // auto-generated ID
        val chatId = docRef.id

        val participants = listOf(currentUserId, participantId)

        val participantMeta = mapOf(
            currentUserId to ParticipantMeta(), // default values
            participantId to ParticipantMeta()
        )

        val conversation = FirestoreConversationModel(
            id = chatId,
            chatType = "direct",
            participants = participants,
            lastMessage = "",
            lastMessageTimestamp = null,
            participantMeta = participantMeta,
            title = null,
            groupPhotoUrl = null,
            otherParticipantName = otherParticipantName,
            otherParticipantAvatar = ""
        )

//        val messagesCollection = docRef.collection("messages")
//        val initialMessage = mapOf(
//            "id" to messagesCollection.id,
//            "content" to "",
//            "senderId" to currentUserId,
//            "timestamp" to com.google.firebase.Timestamp.now(),
//            "messageType" to "text"
//        )
//
//        messagesCollection.document().set(initialMessage).await()

        docRef.set(conversation).await()

        return chatId
    }

}