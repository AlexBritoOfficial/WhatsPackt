package com.packt.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = Conversation::class,
            parentColumns = arrayOf("conversationId"),
            childColumns = arrayOf("conversation_id"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["conversation_id"])
    ]
)

data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "conversation_id")
    val conversationId: String,
    @ColumnInfo(name = "sender")
    val sender: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: String
) {

    companion object {

        fun fromDomain(id: String?,
                       senderName: String,
                       content: String,
                       timestamp: String): Message{
         return Message(   id = 0,
             conversationId = id!!,
             sender = senderName,
             content = content,
             timestamp = timestamp)
        }
    }
}

