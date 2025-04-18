package com.packt.chat.domain.usecases

import com.packt.chat.data.network.datasource.repository.MessageRepository
import javax.inject.Inject

class InsertMessageLocally @Inject constructor(private val repository: MessageRepository) {
    suspend operator fun invoke(message: com.packt.data.database.Message): Long {
        return repository.insertMessageLocally(message = message)
    }
}