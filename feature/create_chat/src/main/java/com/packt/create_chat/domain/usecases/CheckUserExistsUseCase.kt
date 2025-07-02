package com.packt.create_chat.domain.usecases

import com.packt.create_chat.domain.CreateChatRepository
import javax.inject.Inject

class CheckUserExistsUseCase @Inject constructor(
    private val repository: CreateChatRepository
) {
    suspend operator fun invoke(username: String): Boolean {
        return repository.checkUserExists(username)
    }
}