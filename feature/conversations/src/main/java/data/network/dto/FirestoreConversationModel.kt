package data.network.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class FirestoreConversationModel(
    val id: String = "",
    val participants: List<String> = emptyList(),
    val lastMessage: String = "",
    @ServerTimestamp
    val lastMessageTimestamp: Timestamp? = null,
    @ServerTimestamp
    val createdAt: Timestamp? = null,
    val chatType: String = "private",
    val title: String? = null,
    val profileImageUrl: String? = null,
    val unreadCount: Int = 0  // ✅ New field
) {
    // Required no-arg constructor for Firebase
    constructor() : this(
        id = "",
        participants = emptyList(),
        lastMessage = "",
        lastMessageTimestamp = null,
        createdAt = null,
        chatType = "private",
        title = null,
        profileImageUrl = null,
        unreadCount = 0
    )

}
