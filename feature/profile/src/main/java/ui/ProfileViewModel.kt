package ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.domain.GetCurrentUserIdUseCase
import com.packt.domain.GetUserDataUseCase
import com.packt.domain.UpdateUserProfileUseCase
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
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
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

                if (userData.isSuccess){
                    _currentUser.value = UserDataState.Success(userData.getOrNull()!!)
                }

            } catch (e: Exception) {
                _currentUser.value = UserDataState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateProfile(
        bio: String,
        status: String,
        email: String,
        phone: String,
        keywords: String
    ) {
        val state = _currentUser.value
        if (state !is UserDataState.Success) {
            // Not in a state with user data — cannot update
            return
        }

        val currentUserData = state.data

        val updatedUser = currentUserData.copy(
            bio = bio,
            status = status,
            email = email,
            phone = phone,
            searchKeywords = keywords.split(",").map { it.trim() }
        )

        viewModelScope.launch {
            val result = updateUserProfileUseCase(updatedUser)

            if (result.isSuccess) {
                // Update local state so UI reflects the change
                _currentUser.value = UserDataState.Success(updatedUser)
            } else {
                _currentUser.value = UserDataState.Error(result.exceptionOrNull()?.message ?: "Update failed")
            }
        }
    }



}