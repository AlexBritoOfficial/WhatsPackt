package com.packt.login.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.domain.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.model.LoginState
import com.packt.domain.GetCurrentUserIdUseCase
import domain.usecase.LoginWithUsernameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val loginUseCase: LoginWithUsernameUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val loginResult = loginUseCase(username, password)
            if (loginResult.isSuccess) {
                val uid = getCurrentUserIdUseCase()
                if (uid != null) {
                    val userResult = getUserDataUseCase(uid)
                    _loginState.value = userResult.fold(
                        onSuccess = { LoginState.Success },
                        onFailure = { LoginState.Error(it.message ?: "Failed to fetch user data") }
                    )
                } else {
                    _loginState.value = LoginState.Error("User ID not found after login")
                }
            } else {
                _loginState.value = LoginState.Error(loginResult.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}