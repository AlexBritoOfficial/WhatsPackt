package data.network.datasource.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import data.network.dto.FirestoreConversationModel
import data.network.dto.UserModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FireStoreConversationsRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    companion object {
        private const val TAG = "ConversationsRemoteDataSource"
    }

    fun getConversations(userId: String): Flow<List<FirestoreConversationModel>> = callbackFlow {
        val query = firestore
            .collection("chats")
            .whereArrayContains("participants", userId)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot == null || snapshot.isEmpty) {
                trySend(emptyList())
                return@addSnapshotListener
            }

            val results = mutableListOf<FirestoreConversationModel>()
            var pending = snapshot.size()

            snapshot.documents.forEach { doc ->
                val chat = doc.toObject<FirestoreConversationModel>()?.copy(id = doc.id)
                    ?: return@forEach

                val otherUserId = chat.participants.firstOrNull { it != userId }

                if (otherUserId == null) {
                    pending--
                    if (pending == 0) trySend(results)
                    return@forEach
                }

                firestore.collection("users").document(otherUserId)
                    .get()
                    .addOnSuccessListener { userSnapshot ->
                        val user = userSnapshot.toObject<UserModel>()
                        val enriched = chat.copy(
                            otherParticipantName = user?.senderName ?: "Unknown",
                            otherParticipantAvatar = user?.senderAvatar ?: ""
                        )
                        results.add(enriched)
                        pending--
                        if (pending == 0) trySend(results)
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "Failed to fetch user $otherUserId", it)
                        pending--
                        if (pending == 0) trySend(results)
                    }
            }
        }

        awaitClose { listener.remove() }
    }
}
