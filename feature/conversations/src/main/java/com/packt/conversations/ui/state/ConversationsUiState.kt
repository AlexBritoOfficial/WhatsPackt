package com.packt.conversations.ui.state

sealed class ConversationsUiState {
    object Loading : ConversationsUiState()
    object Success : ConversationsUiState()
    data class Error(val message: String) : ConversationsUiState()
}