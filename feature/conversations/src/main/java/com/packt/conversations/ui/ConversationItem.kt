package com.packt.conversations.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.packt.framework.ui.Avatar
import data.network.dto.FirestoreConversationModel
import model.Conversation

@Composable
fun ConversationItem(conversation: FirestoreConversationModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Avatar(
            imageUrl = conversation.otherParticipantAvatar.ifEmpty { "default_avatar_url" },
            size = 50.dp,
            contentDescription = "${conversation.otherParticipantName}'s avatar"
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = conversation.otherParticipantName.ifEmpty { "Unknown" },
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            Text(text = conversation.lastMessage)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = conversation.lastMessageTimestamp?.toDate()?.toString() ?: "",
            )
//            if (conversation. > 0) {
//                Text(
//                    text = conversation.unreadCount.toString(),
//                    color = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier.padding(top = 4.dp)
//                )
//            }
        }
    }
}

