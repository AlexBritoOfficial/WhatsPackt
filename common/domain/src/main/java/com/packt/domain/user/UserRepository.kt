package com.packt.domain.user

import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun getUserData(uid: String): Result<UserData>
    fun getCurrentUserId(): String?
    val currentUserData: StateFlow<UserData?>
    suspend fun setCurrentUserData(userData: UserData)
    fun clearCurrentUserData()
}
