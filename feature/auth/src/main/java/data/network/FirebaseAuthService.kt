package data.network

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.packt.data.network.FirestoreUserModel
import com.packt.data.toDomain
import com.packt.domain.user.UserData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun registerWithEmail(email: String, password: String): Result<AuthResult> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(authResult)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginWithUsername(username: String, password: String): Result<UserData> {
        return try {
            // 1. Find email by username
            val querySnapshot = firestore.collection("users")
                .whereEqualTo("username", username)
                .get()
                .await()

            if (querySnapshot.isEmpty) {
                return Result.failure(Exception("Username not found"))
            }

            val userDoc = querySnapshot.documents.first()

            val email = userDoc.getString("email")
                ?: return Result.failure(Exception("Email missing for user"))

            val firestoreUserModel = userDoc.toObject(FirestoreUserModel::class.java)
                ?: return Result.failure(Exception("User profile data not found"))

            // 2. Sign in with email/password
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: return Result.failure(Exception("User is null"))

            // 3. Return the userData (since we already have it)
            val userData = firestoreUserModel.toEntity().toDomain()
            Result.success(userData)

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