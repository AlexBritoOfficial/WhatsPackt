package data.network.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class FirestoreConversationModel(
    val id: String = "",
    val chatType: String = "direct",
    val participants: List<String> = emptyList(),
    val lastMessage: String = "",
    val lastMessageTimestamp: com.google.firebase.Timestamp? = null,
    val participantMeta: Map<String, ParticipantMeta> = emptyMap(),
    val title: String? = null,
    val groupPhotoUrl: String? = null,
    val otherParticipantName: String = "",
    val otherParticipantAvatar: String = ""
)

data class ParticipantMeta(
    val unreadCount: Int = 0,
    val lastRead: com.google.firebase.Timestamp? = null
)
