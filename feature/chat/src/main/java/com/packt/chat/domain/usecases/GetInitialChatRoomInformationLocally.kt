package com.packt.chat.domain.usecases

import com.packt.chat.domain.models.IMessageRepository
import com.packt.data.database.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInitialChatRoomInformationLocally @Inject constructor(private val repository: IMessageRepository) {

    suspend operator fun invoke(chat_id: String): Flow<List<Message>> {
        return repository.getInitialChatRoomInformationRemoteLocally(chat_id = chat_id.toInt())
    }
}