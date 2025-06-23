package data.network

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun registerWithEmail(email: String, password: String): Result<AuthResult> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(authResult)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginWithEmail(email: String, password: String): Result<AuthResult> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(authResult)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}