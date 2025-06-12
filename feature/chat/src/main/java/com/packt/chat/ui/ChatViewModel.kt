package com.packt.chat.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.packt.chat.domain.models.ChatRoom
import com.packt.chat.domain.usecases.DisconnectMessages
import com.packt.chat.domain.usecases.GetInitialChatRoomInformation
import com.packt.chat.domain.usecases.InsertConversationLocally
import com.packt.chat.domain.usecases.InsertMessageLocally
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val observeMessages: ObserveMessages,
    private val sendMessage: SendMessage,
    private val disconnectMessages: DisconnectMessages,
    private val getInitialChatRoomInformation: GetInitialChatRoomInformation,
    private val insertMessageLocally: InsertMessageLocally,
    private val insertConversationLocally: InsertConversationLocally,
    private val getUserData: GetUserData
) : ViewModel() {

    companion object {
        private const val TAG = "ChatViewModel"
    }

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _uiState = MutableStateFlow(Chat())
    val uiState: StateFlow<Chat> = _uiState

    private var messageCollectionJob: Job? = null

    private lateinit var chatRoom: ChatRoom

    fun loadAndObserveChat(chatId: String) {
        messageCollectionJob?.cancel()
        messageCollectionJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = getUserData.getData()
                val userId = user.id

                // Step 1: Load initial chat room
                chatRoom = getInitialChatRoomInformation(userId, chatId)

                Log.d("ChatViewModel", "Updated UI state with: ${chatRoom.toUI()}")

//                chatRoom.lastMessages.forEach { message ->
//                    insertMessageLocally(
//                        message = com.packt.data.database.Message(
//                            id = chatId,
//                            chatId = chatId,// Auto-generated primary key, so 0 is fine // Pass the chat/conversation id
//                            content = message.content,
//                            contentType = message.contentType.toString(),
//                            contentDescription = message.contentDescription,
//                            timestamp = message.timestamp
//                        )
//                    )
//                }


                withContext(Dispatchers.Main) {
                    Log.d("ChatViewModel", "Updated UI state with: ${chatRoom.toUI()}")
                    _uiState.value = chatRoom.toUI()
                    _messages.value = chatRoom.lastMessages.map { it.toUi() }
                }

                // Step 2: Observe new messages
                observeMessages(userId, chatId)
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to load chat: ${e.localizedMessage}")
            }
        }
    }

    private suspend fun observeMessages(userId: String, chatId: String) {

        observeMessages.invoke(userId = userId, chatId = chatId).collect { newMessage ->
            Log.d(TAG, "New message received: $newMessage")

            insertMessageLocally(
                message = com.packt.data.database.Message(
                    id = chatId,
                    chatId = chatId,// Auto-generated primary key, so 0 is fine // Pass the chat/conversation id
                    content = newMessage.content,
                    contentType = newMessage.contentType.toString(),
                    contentDescription = newMessage.contentDescription,
                    timestamp = newMessage.timestamp!!
                )
            )

            withContext(Dispatchers.Main) {
                _messages.update { current -> current + newMessage.toUi() }
            }
        }
    }

    fun onSendMessage(messageText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserData.getData()

            val timestamp = Timestamp.now()

            val message = DomainMessage(
                id = user.id, // maps to senderId in Firestore
                timestamp = timestamp.toDate().time.toString(), // store as string in domain, Firestore will use Timestamp
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
            senderName = chatRoom.senderName,
            senderAvatar = chatRoom.senderAvatar,
            timestamp = timestamp ?: "",
            isMine = isMine,
            messageContent = getMessageContent()
        )
    }

    private fun DomainMessage.getMessageContent(): MessageContent {
        return when (contentType) {
            DomainMessage.ContentType.TEXT -> MessageContent.TextMessage(content)
            DomainMessage.ContentType.VIDEO -> MessageContent.VideoMessage(
                content,
                contentDescription
            )
            DomainMessage.ContentType.IMAGE -> MessageContent.ImageMessage(
                content,
                contentDescription
            )
        }
    }
}
