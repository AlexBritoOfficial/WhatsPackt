package com.packt.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages WHERE id = :conversation_id ORDER BY timestamp ASC")
    fun getMessagesInConversation(conversation_id: Int): Flow<List<Message>>

    @Insert
    suspend fun insertMessage(message: Message): Long

    @Delete
    suspend fun deleteMessage(message: Message)
}