package ui.state

import com.packt.domain.user.UserData

sealed class UserDataState {
    object Loading : UserDataState()
    data class Success(val data: UserData) : UserDataState()
    data class Loaded(val data: UserData) : UserDataState()
    data class Error(val message: String) : UserDataState()
}