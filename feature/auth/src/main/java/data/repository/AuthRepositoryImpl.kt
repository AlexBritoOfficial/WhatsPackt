package data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.packt.domain.user.UserData
import data.network.FirebaseAuthService
import domain.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: FirebaseAuthService,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun registerWithEmail(email: String, password: String, username: String): Result<Boolean> {
        return try {
            val authResultResult = authService.registerWithEmail(email, password)
            if (authResultResult.isFailure) return Result.failure(authResultResult.exceptionOrNull()!!)

            val authResult = authResultResult.getOrNull()!!
            val uid = authResult.user?.uid ?: return Result.failure(Exception("UID not found"))

            val userDoc = hashMapOf(
                "userId" to uid,
                "username" to username,
                "email" to email,
                "displayName" to username,
                "createdAt" to com.google.firebase.Timestamp.now()
            )

            firestore.collection("users").document(uid).set(userDoc).await()

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun loginWithUsername(username: String, password: String): Result<UserData> {
        return authService.loginWithUsername(username, password)
    }


    override fun logout() {
        authService.logout()
    }

    override fun isUserLoggedIn(): Boolean {
        return authService.isUserLoggedIn()
    }
}