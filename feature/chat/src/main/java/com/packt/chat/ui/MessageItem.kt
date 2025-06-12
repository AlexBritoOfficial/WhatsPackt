package com.packt.chat.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.packt.chat.ui.model.Message
import com.packt.chat.ui.model.MessageContent
import com.packt.framework.ui.Avatar

@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start
    ) {
        // Show avatar if not from the current user
        if (!message.isMine) {
            Avatar(
                imageUrl = message.senderAvatar,
                size = 40.dp,
                contentDescription = "${message.senderName}'s avatar"
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column {
            if (!message.isMine) {
                Text(
                    text = message.senderName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            when (val content = message.messageContent) {
                is MessageContent.TextMessage -> {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (message.isMine) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    ) {
                        Text(
                            text = content.message,
                            modifier = Modifier.padding(8.dp),
                            color = if (message.isMine) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSecondary
                            }
                        )
                    }
                }

                is MessageContent.ImageMessage -> {
                    AsyncImage(
                        model = content.imageUrl,
                        contentDescription = content.contentDescription,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                is MessageContent.VideoMessage -> {
                    // TODO: Handle video rendering
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.timestamp,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
