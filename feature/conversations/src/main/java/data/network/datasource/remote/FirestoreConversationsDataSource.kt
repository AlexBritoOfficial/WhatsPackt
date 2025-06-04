package data.network.datasource.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import data.network.dto.FirestoreConversationModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ConversationsRemoteDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    companion object {
        const val TAG = "ConversationsRemoteDataSource"
    }


    fun getConversations(userId: String): Flow<List<FirestoreConversationModel>> = callbackFlow {
        val query = firebaseFirestore
            .collection("chats")
            .whereArrayContains("participants", userId)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val conversations = mutableListOf<FirestoreConversationModel>()
                var pendingLookups = snapshot.documents.size

                if (pendingLookups == 0) {
                    trySend(conversations).isSuccess
                    return@addSnapshotListener
                }

                snapshot.documents.forEach { document ->
                    val fireStoreConversationModel = document.toObject<FirestoreConversationModel>() ?: return@forEach
                    val chatId = document.id

                    if (!fireStoreConversationModel.participants.contains(userId)) {
                        pendingLookups--
                        return@forEach
                    }

                    val otherUserId = fireStoreConversationModel.participants.firstOrNull { it != userId }
                    if (otherUserId == null) {
                        pendingLookups--
                        return@forEach
                    }

                    // 🔄 Fetch the latest message
                    firebaseFirestore
                        .collection("chats")
                        .document(chatId)
                        .collection("messages")
                        .orderBy("timestamp", Query.Direction.DESCENDING)
                        .get()
                        .addOnSuccessListener { messagesSnapshot ->
                            val messageDoc = messagesSnapshot.documents.firstOrNull()
                            val otherName = messageDoc?.getString("senderName") ?: ""
                            val otherAvatar = messageDoc?.getString("senderAvatar") ?: ""

                            val enriched = fireStoreConversationModel.copy(
                                id = chatId,
                                otherParticipantName = otherName,
                                otherParticipantAvatar = otherAvatar
                            )

                            conversations.add(enriched)
                            pendingLookups--

                            if (pendingLookups == 0) {
                                trySend(conversations).isSuccess
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Failed to fetch latest message for chat $chatId", e)
                            pendingLookups--
                            if (pendingLookups == 0) {
                                trySend(conversations).isSuccess
                            }
                        }
                }
            }
        }

        awaitClose { listener.remove() }
    }

}
