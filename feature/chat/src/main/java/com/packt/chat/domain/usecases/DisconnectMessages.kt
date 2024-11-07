package com.packt.chat.domain.usecases

import com.packt.chat.domain.models.IMessageRepository
import javax.inject.Inject

class DisconnectMessages @Inject constructor(private val repository: IMessageRepository) {

    suspend operator fun invoke(){
        repository.disconnect()
    }
}