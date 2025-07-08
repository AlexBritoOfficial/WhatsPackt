package com.packt.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.packt.data.model.UserDataEntity
import com.packt.domain.user.UserData


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
}

