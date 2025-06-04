package data.network.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import data.network.dto.FirestoreConversationModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ConversationsRemoteDataSource @Inject constructor(private val firebaseFirestore: FirebaseFirestore) {

    companion object {
        const val TAG = "FireStoreConversationsDataSource"
    }

    fun getConversations(userId: String): Flow<List<FirestoreConversationModel>> = callbackFlow {
        val collection = firebaseFirestore.collection("chats")

        collection.document("chatId1").delete()
        collection.document("chatId2").delete()


        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error) // closes the flow with exception
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val conversations = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(FirestoreConversationModel::class.java)?.copy(id = doc.id)
                }
                trySend(conversations).isSuccess
            }
        }

        // Clean up the listener when the flow is closed or cancelled
        awaitClose { listener.remove() }
    }
}