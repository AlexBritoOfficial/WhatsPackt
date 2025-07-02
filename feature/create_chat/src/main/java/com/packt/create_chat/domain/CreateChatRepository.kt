package com.packt.create_chat.domain

interface CreateChatRepository {
    suspend fun checkUserExists(username: String): Boolean
    suspend fun createChat(    currentUserId: String,
                               participantId: String,
                               otherParticipantName: String): String
    suspend fun getParticipantId(username: String): String
}