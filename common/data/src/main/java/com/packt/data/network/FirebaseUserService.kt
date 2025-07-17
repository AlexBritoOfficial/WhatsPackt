package com.packt.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.packt.data.model.UserDataEntity
import com.packt.domain.model.AuthStatus
import com.packt.domain.model.UserData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class FirebaseUserService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun getUserData(uid: String): UserDataEntity {
        val querySnapshot = firestore.collection("users")
            .whereEqualTo("userId", uid)
            .get()
            .await()

        if (querySnapshot.isEmpty) {
            throw Exception("User data not found for uid: $uid")
        }

        val userDataEntity = querySnapshot.documents.first().toObject(UserDataEntity::class.java)
            ?: throw Exception("Failed to deserialize UserDataEntity for uid: $uid")

        return userDataEntity
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    suspend fun updateUserData(userData: UserData): Result<Unit> {
        return try {
            val firestoreModel = FirestoreUserModel.fromDomain(userData)
            firestore
                .collection("users")
                .document(userData.id)
                .set(firestoreModel)
                .await()  // if you're using kotlinx-coroutines-play-services
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

     fun observeAuthStatus(): Flow<AuthStatus> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(
                if (auth.currentUser != null) AuthStatus.Authenticated
                else AuthStatus.Unauthenticated
            )
        }

        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    suspend fun logout(){
        firebaseAuth.signOut()
    }
}


