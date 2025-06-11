package data.network.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class UserModel(
    @get:PropertyName("avatarUrl") @set:PropertyName("avatarUrl")
    var senderAvatar: String? = null,

    @get:PropertyName("displayName") @set:PropertyName("displayName")
    var senderName: String? = null,

    val lastActive: Timestamp? = null,

    val phone: String? = null,

    val status: String? = null,

    val username: String? = null
)
