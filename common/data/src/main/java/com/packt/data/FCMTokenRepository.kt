package com.packt.data

import javax.inject.Inject

class FCMTokenRepository @Inject constructor(private val fcmTokenDataSource: FCMTokenDataSource) {

    suspend fun getFCMToken(): String? {
        return fcmTokenDataSource.getFCMToken()
    }
}