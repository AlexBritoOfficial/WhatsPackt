package com.packt.data

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FCMTokenDataSource @Inject constructor( val firebaseMessaging: FirebaseMessaging)  {

    suspend fun getFcMToken(): String? {
        return try {
           firebaseMessaging.token.await()
        } catch (e: Exception) {
            null
        }
    }
}