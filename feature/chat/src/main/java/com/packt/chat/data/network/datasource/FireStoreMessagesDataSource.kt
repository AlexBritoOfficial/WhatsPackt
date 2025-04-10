package com.packt.chat.data.network.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.packt.chat.data.network.model.FireStoreMessageModel
import com.packt.chat.domain.models.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FireStoreMessagesDataSource @Inject constructor(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    fun getMessages(chatId: String, userId: String): Flow<Message> = callbackFlow {

        // Create a reference to the "chats" collection in Firestore,
        // and then a subcollection called "messages" where we use chatId as the document ID.
        val chatReference = firestore.collection("chats")
            .document("chatId")
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

                // Map the document to a FireStoreMessageModel object
                val message = document.toObject(FireStoreMessageModel::class.java)

                // Copy the message with the document ID and emit it
                message?.copy(id = document.id)

            } ?: emptyList()

            // Convert the list of FireStoreMessageModel objects into a list of Message objects
            val domainMessages = messages.map { it.toDomain(userId) }

            // Emit each message in the domainMessages list
            domainMessages.forEach { message ->
                trySend(message)
            }
        }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    fun sendMessage(chatId: String, message: Message){

        // Create a reference to the "chats" collection in Firestore,
        val chatReference = firestore.collection("chats")
            .document("chatId")
            .collection("messages")

        // Send the message to Firestore
        chatReference.add(FireStoreMessageModel.fromDomain(message))
    }
}