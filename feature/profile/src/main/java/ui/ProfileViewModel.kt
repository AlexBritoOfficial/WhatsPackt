package ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.domain.GetCurrentUserIdUseCase
import com.packt.domain.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.state.UserDataState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserDataState>(UserDataState.Loading)
    val currentUser: StateFlow<UserDataState> = _currentUser

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            try {
                // Get current UID
                val uid = getCurrentUserIdUseCase()

                // Load user data
                val userData = getUserDataUseCase(uid)
                _currentUser.value = UserDataState.Success(userData.getOrNull()!!)

            } catch (e: Exception) {
                _currentUser.value = UserDataState.Error(e.message ?: "Unknown error")
            }
        }
    }

}