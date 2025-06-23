package com.packt.login.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.AuthRepository
import domain.model.RegisterState
import domain.usecase.RegisterWithEmailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerWithEmailUseCase: RegisterWithEmailUseCase) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun registerWithEmail(username: String, email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            val result = registerWithEmailUseCase(
                email = email,
                password = password,
                username = username
            )
            _registerState.value = if (result.isSuccess) {
                RegisterState.Success
            } else {
                RegisterState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}