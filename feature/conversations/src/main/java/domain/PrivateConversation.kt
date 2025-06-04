package domain

data class PrivateConversation(
    override val id: String,
    override val participants: List<String>,
    override val lastMessage: String,
    override val lastMessageTimestamp: com.google.firebase.Timestamp?,
    override val createdAt: com.google.firebase.Timestamp?
) : Conversation()
