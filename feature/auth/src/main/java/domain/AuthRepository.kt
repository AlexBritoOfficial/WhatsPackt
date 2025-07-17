package domain

import com.packt.domain.model.UserData

interface AuthRepository {
    suspend fun registerWithEmail(email: String, password: String, username: String): Result<Boolean>
    suspend fun loginWithUsername(username: String, password: String): Result<UserData>
    fun logout()
    fun isUserLoggedIn(): Boolean
}


