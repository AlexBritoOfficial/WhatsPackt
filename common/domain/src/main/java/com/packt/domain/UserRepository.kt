package com.packt.domain

import com.packt.domain.model.AuthStatus
import com.packt.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun getUserData(uid: String): Result<UserData>
    fun getCurrentUserId(): String?
    val currentUserData: StateFlow<UserData?>
    suspend fun setCurrentUserData(userData: UserData)
    fun clearCurrentUserData()
    suspend fun updateUserData(userData: UserData): Result<Unit>
    fun observeAuthStatus(): Flow<AuthStatus>
    suspend fun logout()
}
