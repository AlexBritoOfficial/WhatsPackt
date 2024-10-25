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
import model.Conversation

@Composable
fun ConversationItem(conversation: Conversation) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        /** Avatar **/
        Avatar(
            imageUrl = conversation.avatar,
            size = 50.dp,
            contentDescription = "${conversation.name}'s avatar"
        )

        /** Spacer **/
        Spacer(modifier = Modifier.width(8.dp))

        /** Column **/
        Column {

            /** Conversation Name**/
            Text(
                text = conversation.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            /** Conversation Maessage **/
            Text(text = conversation.message)
        }

        /** Spacer **/
        Spacer(modifier = Modifier.width(8.dp))

        /** Column **/
        Column(horizontalAlignment = Alignment.End) {

            /** Conversation Timestamp**/
            Text(text = conversation.timestamp,)

            /** Conversation Unread Messages **/
            if(conversation.unreadCount > 0){
                Text(text = conversation.unreadCount.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 4.dp))
            }
        }

    }
}