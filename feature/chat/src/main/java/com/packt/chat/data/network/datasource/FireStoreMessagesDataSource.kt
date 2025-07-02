package com.packt.chat.data.network.datasource

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.packt.chat.data.network.model.FireStoreMessageModel
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStoreMessagesDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    companion object {
        const val TAG = "FireStoreMessagesDataSource"
    }

    private lateinit var chatRoom: ChatRoom
    fun getInitialChatRoomInformation(userId: String, chatId: String): Flow<ChatRoom> =
        callbackFlow {
            val chatDocRef = firestore.collection("chats").document(chatId)

            val listenerRegistration = chatDocRef.collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val fireStoreMessages = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(FireStoreMessageModel::class.java)?.copy(id = doc.id)
                    } ?: emptyList()

                    val domainMessages = fireStoreMessages.map { it.toMessageDomain(userId) }

                    // Fetch the chat document to get participant IDs
                    chatDocRef.get().addOnSuccessListener { chatSnapshot ->
                        val participants = chatSnapshot.get("participants") as? List<String>
                        val otherUserId = participants?.firstOrNull { it != userId }

                        if (otherUserId != null) {
                            firestore.collection("users").document(otherUserId)
                                .get()
                                .addOnSuccessListener { userSnapshot ->
                                    val otherUserName =
                                        userSnapshot.getString("displayName").orEmpty()
                                    val otherUserAvatar =
                                        userSnapshot.getString("avatarUrl").orEmpty()

                                    chatRoom = ChatRoom(
                                        id = chatId,
                                        senderName = otherUserName,
                                        senderAvatar = otherUserAvatar,
                                        lastMessages = domainMessages
                                    )
                                    trySend(chatRoom)
                                }
                                .addOnFailureListener { e ->
                                    close(e)
                                }
                        } else {
                            close(Exception("Could not find other participant"))
                        }
                    }.addOnFailureListener { e ->
                        close(e)
                    }
                }

            awaitClose { listenerRegistration.remove() }
        }


//    fun observeMessages(userId: String, chatId: String): Flow<Message> = callbackFlow {
//        val chatReference = firestore.collection("chats")
//            .document(chatId)
//            .collection("messages")
//            .orderBy("timestamp", Query.Direction.ASCENDING)
//
//        val listenerRegistration = chatReference.addSnapshotListener { snapshot, error ->
//            if (error != null) {
//                close(error)
//                return@addSnapshotListener
//            }
//
//            if (snapshot != null) {
//                for (change in snapshot.documentChanges) {
//                    if (change.type == DocumentChange.Type.ADDED) {
//                        val fireStoreMsg = change.document.toObject(FireStoreMessageModel::class.java)
//                        val domainMsg = fireStoreMsg.toMessageDomain(currentUserId = userId)
//                        trySend(domainMsg)
//                    }
//                }
//            }
//        }
//
//        awaitClose { listenerRegistration.remove() }
//    }

    fun observeMessages(userId: String, chatId: String): Flow<Message> = callbackFlow {

        // Create a reference to the "chats" collection in Firestore,
        // and then a subcollection called "messages" where we use chatId as the document ID.
        val chatReference = firestore.collection("chats")
            .document(chatId)
            .collection("messages").orderBy("timestamp", Query.Direction.ASCENDING)

        var isFirstSnapshot = true

        // Add a snap shot listener to the query.
        val listenerRegistration = chatReference.addSnapshotListener { snapshot, error ->

            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                if (isFirstSnapshot) {
                    isFirstSnapshot = false
                    return@addSnapshotListener // skip first batch
                }
                for (change in snapshot.documentChanges) {

                    if (change.type == DocumentChange.Type.ADDED) {
                        try {
                            val fireStoreMsg =
                                change.document.toObject(FireStoreMessageModel::class.java)
                            val domainMsg = fireStoreMsg.toMessageDomain(currentUserId = userId)
                            trySend(domainMsg)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        awaitClose {
            listenerRegistration.remove()
        }
    }



    fun sendMessage(chatId: String, message: Message) {

        // Create a reference to the "chats" collection in Firestore,
        val chatReference = firestore.collection("chats")
            .document(chatId)
            .collection("messages")

        // Send the message to Firestore
        chatReference.add(FireStoreMessageModel.fromDomain(message))
    }

    fun disconnect() {
        firestore.terminate()
    }
}