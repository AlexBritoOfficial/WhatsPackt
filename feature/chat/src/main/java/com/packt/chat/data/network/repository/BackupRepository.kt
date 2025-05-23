package com.packt.chat.data.network.repository

import com.google.gson.Gson
import com.packt.chat.data.network.datasource.StorageDataSource
import com.packt.data.database.ConversationDao
import com.packt.data.database.MessageDao
import java.io.File
import javax.inject.Inject

class BackupRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val conversationDao: ConversationDao,
    private val storageDataSource: StorageDataSource
) {

    private val gson = Gson()

    private val tempDir = System.getProperty("java.io.tmpdir")

    suspend fun backupAllConversations() {

        // Get all converstations from the database
        val conversations = conversationDao.getAllConversations()

        conversations.collect {
            for (conversation in it) {

                val messages =
                    messageDao.getMessagesInConversation(conversation_id = conversation.conversationId.toInt())

                // Create a JSON representation of the messages
                val messagesJson = gson.toJson(messages)

                // Create a temporary file and write the JSON to it.
                val tempFile = createTempFile("messages", ".json");

                tempFile.writeText(messagesJson)

                // Upload the file to Firebase Storage
                val remotePath = "conversations/{${conversation.conversationId}/messages}"
                storageDataSource.uploadFile(tempFile, remotePath)

                // Delete the local file
                tempFile.delete()
            }
        }
    }



    private fun createTempFile(prefix: String, suffix: String): java.io.File {

        // Specify the directory to where the temporary file will be located
        val tempDir = System.getProperty(tempDir)?.let { java.io.File(it) }

        // Create a temporary file with a prefix and a suffix
        return File.createTempFile(prefix, suffix, tempDir)

    }

}