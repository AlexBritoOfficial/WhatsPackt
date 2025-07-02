package com.packt.create_chat.presentation.state

sealed class CreateChatUiState {
    object Idle : CreateChatUiState()
    object Loading : CreateChatUiState()
    data class Success(val chatId: String) : CreateChatUiState()
    data class Error(val message: String) : CreateChatUiState()
}