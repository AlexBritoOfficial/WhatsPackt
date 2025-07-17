// common/data/repository/UserRepositoryImpl.kt
package com.packt.data.repository

import com.packt.data.network.FirebaseUserService
import com.packt.data.toDomain
import com.packt.domain.model.AuthStatus
import com.packt.domain.model.UserData
import com.packt.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseUserService: FirebaseUserService
) : UserRepository {

    // Backing flow to hold the current user
    private val _currentUserData = MutableStateFlow<UserData?>(null)
    override val currentUserData: StateFlow<UserData?> = _currentUserData

    override suspend fun getUserData(uid: String): Result<UserData> {
        return try {
            val entity = firebaseUserService.getUserData(uid)
            Result.success(entity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUserId(): String? {
        return firebaseUserService.getCurrentUserId()
    }

    override suspend fun setCurrentUserData(userData: UserData) {
        _currentUserData.value = userData
    }

    override fun clearCurrentUserData() {
        _currentUserData.value = null
    }

    override suspend fun updateUserData(userData: UserData): Result<Unit> {
        return firebaseUserService.updateUserData(userData)
    }

    override fun observeAuthStatus(): Flow<AuthStatus> {
        return firebaseUserService.observeAuthStatus()
    }

    override suspend fun logout() {
        firebaseUserService.logout()
    }
}