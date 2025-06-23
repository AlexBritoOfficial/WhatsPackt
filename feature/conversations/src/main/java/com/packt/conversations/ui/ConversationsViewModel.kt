package com.packt.conversations.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.conversations.ui.mapper.toUiModel
import com.packt.conversations.ui.state.ConversationsUiState
import com.packt.domain.GetCurrentUserIdUseCase
import com.packt.domain.GetUserDataUseCase
import data.network.dto.FirestoreConversationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.GetAllConversations
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(private val getAllConversations: GetAllConversations,
                                                 private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase): ViewModel() {

    private val _conversations = MutableStateFlow<List<FirestoreConversationModel>>(emptyList())
    val conversations: StateFlow<List<FirestoreConversationModel>> = _conversations
    private val _uiState = MutableStateFlow(ConversationsUiState.Loading)


    private lateinit var messageCollectionJob: Job


    fun getConversations(){
        messageCollectionJob = viewModelScope.launch {
            _conversations.value = getAllConversations(getCurrentUserIdUseCase())
        }

    }
}