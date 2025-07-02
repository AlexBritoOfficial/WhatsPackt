package com.packt.create_chat.domain.usecases

import com.packt.create_chat.domain.CreateChatRepository
import javax.inject.Inject

class GetParticipantIdUseCase @Inject constructor(
    private val repository: CreateChatRepository
) {
    suspend operator fun invoke(
        otherParticipantName: String
    ): String {
        return repository.getParticipantId(
            username = otherParticipantName,
        )
    }
}