package com.packt.login.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.domain.model.AuthStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.model.LoginState
import com.packt.domain.UserRepository
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
    private val _authStatus = MutableStateFlow<AuthStatus>(AuthStatus.Unauthenticated)
    val authStatus: StateFlow<AuthStatus> = _authStatus

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val loginResult = loginUseCase(username, password)

            loginResult
                .onSuccess { userData ->
                    userRepository.setCurrentUserData(userData)
                    _authStatus.value = AuthStatus.Authenticated
                    _loginState.value = LoginState.Success
                }
                .onFailure { error ->
                    _loginState.value = LoginState.Error(
                        error.localizedMessage ?: "Login failed"
                    )
                    _authStatus.value = AuthStatus.Unauthenticated
                }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}