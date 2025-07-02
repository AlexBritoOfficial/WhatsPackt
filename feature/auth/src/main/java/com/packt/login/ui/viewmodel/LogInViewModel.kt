package com.packt.login.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.model.LoginState
import com.packt.domain.user.UserRepository
import domain.usecase.LoginWithUsernameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val loginUseCase: LoginWithUsernameUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val loginResult = loginUseCase(username, password)

            loginResult
                .onSuccess { userData ->
                    userRepository.setCurrentUserData(userData)
                    _loginState.value = LoginState.Success
                }
                .onFailure { error ->
                    _loginState.value = LoginState.Error(
                        error.localizedMessage ?: "Login failed"
                    )
                }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}