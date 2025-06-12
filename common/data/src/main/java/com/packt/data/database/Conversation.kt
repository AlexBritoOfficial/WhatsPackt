package com.packt.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")

data class Conversation(
    @PrimaryKey
    val chatId: String, // Firestore Document ID of the chat

    val chatType: String?,
    val createdAt: Long?, // Store Timestamp as epoch millis
    val lastMessage: String?,
    val lastMessageTimestamp: Long?,
    val profileImageUrl: String?,
    val title: String?,
    val unreadAccount: String?,
    val unreadCount: Int?
)