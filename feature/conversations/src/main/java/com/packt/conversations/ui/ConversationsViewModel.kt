package com.packt.conversations.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.conversations.ui.state.ConversationsUiState
import com.packt.conversations.ui.state.UserDataState
import com.packt.domain.GetCurrentUserIdUseCase
import com.packt.domain.GetUserDataUseCase
import com.packt.domain.LogoutUserUseCase
import com.packt.domain.ObserveAuthStatusUseCase
import com.packt.domain.model.AuthStatus
import data.network.dto.FirestoreConversationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.GetAllConversations
import domain.PrivateConversation
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val getAllConversations: GetAllConversations,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val observeAuthStatusUseCase: ObserveAuthStatusUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel() {

    private val _conversations = MutableStateFlow<List<FirestoreConversationModel>>(emptyList())
    val conversations: StateFlow<List<FirestoreConversationModel>> = _conversations

    private val _uiState = MutableStateFlow<ConversationsUiState>(ConversationsUiState.Loading)
    val uiState: StateFlow<ConversationsUiState> = _uiState

    private val _currentUser = MutableStateFlow<UserDataState>(UserDataState.Loading)
    val currentUser: StateFlow<UserDataState> = _currentUser

    private lateinit var messageCollectionJob: Job

    private val _authState = MutableStateFlow<AuthStatus>(AuthStatus.Authenticated)
    val authState: StateFlow<AuthStatus> = _authState

    init {
        observeAuthStatus()
        loadCurrentUserAndConversations()
    }

    private fun loadCurrentUserAndConversations() {
        viewModelScope.launch {
            try {
                // Get current UID
                val uid = getCurrentUserIdUseCase()

                // Load user data
                val userData = getUserDataUseCase(uid)
                _currentUser.value = UserDataState.Success(userData.getOrNull()!!)

                // Load conversations
                _conversations.value = getAllConversations(uid)
                _uiState.value = ConversationsUiState.Success
            } catch (e: Exception) {
                _currentUser.value = UserDataState.Error(e.message ?: "Unknown error")
                _uiState.value = ConversationsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getConversations() {
        messageCollectionJob = viewModelScope.launch {
            try {
                val uid = getCurrentUserIdUseCase()
                _conversations.value = getAllConversations(uid)
                _uiState.value = ConversationsUiState.Success
            } catch (e: Exception) {
                _uiState.value = ConversationsUiState.Error(e.message ?: "Failed to load conversations")
            }
        }
    }

    private fun observeAuthStatus() {
        viewModelScope.launch {
            observeAuthStatusUseCase().collect { authStatus ->
                _authState.value = authStatus
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            logoutUserUseCase()

            observeAuthStatusUseCase().collect { authStatus ->
                _authState.value = authStatus
            }
        }
    }
}
