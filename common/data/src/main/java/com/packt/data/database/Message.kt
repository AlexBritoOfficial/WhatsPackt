package com.packt.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = Conversation::class,
        parentColumns = ["chatId"],
        childColumns = ["chatId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("chatId")]
)
data class Message(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,

    @ColumnInfo(name = "chatId")
    val chatId: String, // This is NOT in Firestore message doc, but from path /chats/{chatId}/messages

    @ColumnInfo(name = "id")
    val id: String? = null,

    @ColumnInfo(name = "timestamp")
    val timestamp: String? = null,

    @ColumnInfo(name = "content_type")
    val contentType: String = "TEXT",

    @ColumnInfo(name = "content")
    val content: String = "",

    @ColumnInfo(name = "content_description")
    val contentDescription: String = ""
)
