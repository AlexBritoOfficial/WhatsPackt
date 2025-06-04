package com.packt.chat.data.network.datasource

import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.packt.chat.data.network.model.FireStoreMessageModel
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FireStoreMessagesDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    companion object {
        const val TAG = "FireStoreMessagesDataSource"
    }

    private lateinit var chatRoom: ChatRoom
    fun getInitialChatRoomInformation(userId: String, chatId: String): Flow<ChatRoom> =
        callbackFlow {
            // Create a reference to the "chats" collection in Firestore,
            // and then a subcollection called "messages" where we use chatId as the document ID.


            val chatReference = firestore.collection("chats")
                .document(chatId)
                .collection("messages")

            // Create a query to order the messages by their timestamp in ascending order.
            val query = chatReference.orderBy("timestamp", Query.Direction.ASCENDING)

            // Add a snap shot listener to the query.
            val listenerRegistration = query.addSnapshotListener { snapshot, error ->

                // if there is an error, close the flow and return
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                // Listening for new messages coming from the Firestore database, if there isn't return an empty list
                val messages = snapshot?.documents?.mapNotNull { document ->

                    Log.d(TAG, "Raw doc: ${document.data}")
                    Log.d(TAG, "Path doc: ${firestore}")

                    // Map the document to a FireStoreMessageModel object
                    val message = document.toObject(FireStoreMessageModel::class.java)

                    // Copy the message with the document ID and emit it
                    message?.copy(id = document.id)

                } ?: emptyList()

                // Convert the list of FireStoreMessageModel objects into a list of Message objects
                val chatRoomDomainMessages = messages.map { it.toMessageDomain(userId = userId) }

                chatRoomDomainMessages.forEach { message ->

                    if (message.id != userId) {
                        chatRoom = ChatRoom(
                            id = chatId,
                            senderName = message.senderName,
                            senderAvatar = message.senderAvatar,
                            lastMessages = chatRoomDomainMessages
                        )
                    }
                }

                trySend(chatRoom)

            }
            awaitClose {
                listenerRegistration.remove()
            }
        }

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
                            val fireStoreMsg = change.document.toObject(FireStoreMessageModel::class.java)
                            val domainMsg = fireStoreMsg.toMessageDomain(userId = userId)
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

    fun disconnect(){
        firestore.terminate()
    }
}