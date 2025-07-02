package domain

import com.google.firebase.auth.AuthResult
import com.packt.domain.user.UserData

interface AuthRepository {
    suspend fun registerWithEmail(email: String, password: String, username: String): Result<Boolean>
    suspend fun loginWithUsername(username: String, password: String): Result<UserData>
    fun logout()
    fun isUserLoggedIn(): Boolean
}


