package com.packt.create_chat.domain.usecases

import com.packt.create_chat.domain.CreateChatRepository
import javax.inject.Inject

class CreateChatUseCase @Inject constructor(
    private val repository: CreateChatRepository
) {
    suspend operator fun invoke(
        currentUserId: String,
        participantId: String,
        otherParticipantName: String
    ): String {
        return repository.createChat(
            currentUserId = currentUserId,
            participantId = participantId,
            otherParticipantName = otherParticipantName,
        )
    }
}