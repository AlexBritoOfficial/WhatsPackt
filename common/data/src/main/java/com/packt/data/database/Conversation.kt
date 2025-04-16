package com.packt.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")

data class Conversation(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "last_message_time")
    val lastMessageTime: Long

)