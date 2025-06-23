package domain

import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun registerWithEmail(email: String, password: String, username: String): Result<Boolean>
    suspend fun loginWithUsername(username: String, password: String): Result<String>
    fun logout()
    fun isUserLoggedIn(): Boolean
}


