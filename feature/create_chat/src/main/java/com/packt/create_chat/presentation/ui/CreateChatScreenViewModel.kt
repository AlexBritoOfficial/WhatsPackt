package com.packt.create_chat.presentation.ui

import androidx.lifecycle.ViewModel
import com.packt.create_chat.presentation.state.CreateChatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.packt.create_chat.domain.usecases.CheckUserExistsUseCase
import com.packt.create_chat.domain.usecases.CreateChatUseCase
import com.packt.create_chat.domain.usecases.GetParticipantIdUseCase
import com.packt.domain.GetCurrentUserIdUseCase
import com.packt.domain.GetUserDataUseCase
import com.packt.domain.user.UserData
import domain.usecase.IsUserLoggedInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateChatScreenViewModel @Inject constructor(
    private val checkUserExistsUseCase: CheckUserExistsUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getParticipantIdUseCase: GetParticipantIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CreateChatUiState>(CreateChatUiState.Idle)
    val uiState: StateFlow<CreateChatUiState> = _uiState

    // Holds the current user's data for UI composables to observe
    private val _currentUserData = MutableStateFlow<UserData?>(null)
    val currentUserData: StateFlow<UserData?> = _currentUserData

    init {
        if (isUserLoggedInUseCase()) {
            loadCurrentUserData()
        }
    }

    private fun loadCurrentUserData() {
        viewModelScope.launch {
            try {
                val userId = getCurrentUserIdUseCase()
                if (userId != null) {
                    val userData = getUserDataUseCase(userId)
                    _currentUserData.value = userData.getOrNull()
                } else {
                    _currentUserData.value = null
                }
            } catch (e: Exception) {
                // Handle error if needed, e.g., log or set null
                _currentUserData.value = null
            }
        }
    }

    /**
     * Starts the process of checking if a user exists and then creating a chat.
     */
    fun startNewChat(
        currentUserId: String,
        otherParticipantName: String,
    ) {
        viewModelScope.launch {
            _uiState.value = CreateChatUiState.Loading

            try {
                // Check if participant exists
                if (!checkUserExistsUseCase(otherParticipantName)) {
                    _uiState.value =
                        CreateChatUiState.Error("Username: '$otherParticipantName' not found.")
                    return@launch
                }

                val otherUserId = getParticipantIdUseCase(otherParticipantName)
                val chatId = createChatUseCase(
                    currentUserId = currentUserId,
                    participantId = otherUserId,
                    otherParticipantName = otherParticipantName,
                )
                _uiState.value = CreateChatUiState.Success(chatId)

            } catch (e: Exception) {
                _uiState.value = CreateChatUiState.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }


    /**
     * Resets the UI state back to Idle.
     */
    fun resetState() {
        _uiState.value = CreateChatUiState.Idle
    }
}