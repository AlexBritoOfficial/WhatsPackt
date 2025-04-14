package com.packt.chat.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.usecases.DisconnectMessages
import com.packt.chat.domain.usecases.GetInitialChatRoomInformation
import com.packt.chat.domain.usecases.ObserveMessages
import com.packt.chat.domain.usecases.SendMessage
import com.packt.chat.ui.model.Chat
import com.packt.chat.ui.model.Message
import com.packt.chat.ui.model.MessageContent
import com.packt.chat.ui.model.toUI
import com.packt.chat.domain.models.Message as DomainMessage
import com.packt.domain.user.GetUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val observeMessages: ObserveMessages,
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

    private val _uiState = MutableStateFlow(Chat())
    val uiState: StateFlow<Chat> = _uiState

    private var messageCollectionJob: Job? = null

    private lateinit var chatRoom: ChatRoom

    // Load Chat Information from Firebase
    fun loadInitialChatInformation(chatId: String) {
        messageCollectionJob = viewModelScope.launch(Dispatchers.IO) {
            try {
               chatRoom = getInitialChatRoomInformation(
                   userId = getUserData.getData().id,
                   chatId = chatId
               )
                withContext(Dispatchers.Main) {
                    _uiState.value = chatRoom.toUI()
                    _messages.value = chatRoom.lastMessages.map { it.toUi() }
                }
            } catch (ie: Throwable) {
                Log.d(
                    "TODO",
                    "You can show here a message to the user indicating that an error has happened"
                )
            }
        }
    }

    fun observeMessages(chatId: String) {
            messageCollectionJob = viewModelScope.launch(Dispatchers.IO) {
                viewModelScope.launch {
                    observeMessages(
                        userId = getUserData.getData().id,
                        chatId = chatId
                    ).collect { message ->
                        Log.d(TAG, "New message received: $message")
                        _messages.update { current ->
                            current + message.toUi()
                        }
                    }
                }
            }
    }

    fun onSendMessage(messageText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserData.getData()

            val message = DomainMessage(
                id = user.id.replace("chatId", "messageId"),
                senderName = user.name,
                senderAvatar = user.avatar,
                isMine = true,
                contentType = DomainMessage.ContentType.TEXT,
                content = messageText,
                contentDescription = messageText
            )
            sendMessage(chatId = chatRoom.id, message)
        }
    }

    override fun onCleared() {
        messageCollectionJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            disconnectMessages()
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
