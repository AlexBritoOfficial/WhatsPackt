package com.packt.chat.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.usecases.DisconnectMessages
import com.packt.chat.domain.usecases.GetInitialChatRoomInformation
import com.packt.chat.domain.usecases.RetrieveMessages
import com.packt.chat.domain.usecases.SendMessage
import com.packt.chat.ui.model.Message
import com.packt.chat.ui.model.MessageContent
import com.packt.chat.domain.models.Message as DomainMessage
import com.packt.domain.user.GetUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val retrieveMessages: RetrieveMessages,
    private val sendMessage: SendMessage,
    private val disconnectMessages: DisconnectMessages,
    private val getInitialChatRoomInformation: GetInitialChatRoomInformation,
    private val getUserData: GetUserData
) : ViewModel() {

    companion object {
        private val TAG = "ChatViewModel"
    }

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private lateinit var chatRoom: ChatRoom

    private var messageCollectionJob: Job? = null


    fun loadChatInformation(chatId: String) {

    }

    fun updateMessages() {
        messageCollectionJob = viewModelScope.launch(Dispatchers.IO) {

            try {
                retrieveMessages(chatId = chatRoom.id, userId = getUserData.getData().id).map {
                    it.toUi()
                }.collect { message ->
                    withContext(Dispatchers.Main) {
                        _messages.value += message
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error loading messages from chat room", e)
            }

        }
    }

    private fun DomainMessage.toUi(): Message {
        return Message(
            id = id ?: "",
            senderName = senderName,
            senderAvatar = senderAvatar,
            timestamp = timestamp ?: "",
            isMine = isMine,
            messageContent = getMessageContent()
        )
    }

    fun onSendMessage(messageText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserData.getData()

            val message = DomainMessage(
                senderName = user.name,
                senderAvatar = user.avatar,
                isMine = true,
                contentType = DomainMessage.ContentType.TEXT,
                content = messageText,
                contentDescription = messageText
            )
            sendMessage(chatId = "1",  message)
        }
    }

    override fun onCleared() {
        messageCollectionJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            disconnectMessages()
        }
    }


    private fun DomainMessage.getMessageContent(): MessageContent {
        return when (contentType) {
            DomainMessage.ContentType.TEXT -> MessageContent.TextMessage(content)
            DomainMessage.ContentType.IMAGE -> MessageContent.ImageMessage(
                content,
                contentDescription
            )
        }
    }

}
