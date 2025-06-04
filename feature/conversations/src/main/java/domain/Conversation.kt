package domain

import com.google.firebase.Timestamp


sealed class Conversation {
    abstract val id: String
    val chatType: String = "private"
    abstract val participants: List<String>
    abstract val lastMessage: String
    abstract val lastMessageTimestamp: Timestamp?
    abstract val createdAt: Timestamp?
}