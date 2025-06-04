package com.packt.conversations.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.network.dto.FirestoreConversationModel
import com.packt.domain.user.GetUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.GetAllConversations
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(private val getAllConversations: GetAllConversations,
    private val getUserData: GetUserData): ViewModel() {

    private val _conversations = MutableStateFlow<List<FirestoreConversationModel>>(emptyList())
    val conversations: StateFlow<List<FirestoreConversationModel>> = _conversations


    private lateinit var messageCollectionJob: Job


     fun getConversations(){
         messageCollectionJob = viewModelScope.launch {
            _conversations.value = getAllConversations(getUserData.getData().id)
        }

    }
}