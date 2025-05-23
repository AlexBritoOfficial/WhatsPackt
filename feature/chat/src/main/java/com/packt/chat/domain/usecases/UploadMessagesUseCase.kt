package com.packt.chat.domain.usecases

import com.packt.chat.data.network.repository.BackupRepository
import javax.inject.Inject

class UploadMessagesUseCase @Inject constructor(private val backupRepository: BackupRepository) {

    suspend operator fun invoke() {
        backupRepository.backupAllConversations()
    }

}