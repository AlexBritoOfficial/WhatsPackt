// common/data/repository/UserRepositoryImpl.kt
package com.packt.data.repository

import com.packt.data.network.FirebaseUserService
import com.packt.data.toDomain
import com.packt.domain.user.UserData
import com.packt.domain.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: FirebaseUserService
) : UserRepository {

    // Backing flow to hold the current user
    private val _currentUserData = MutableStateFlow<UserData?>(null)
    override val currentUserData: StateFlow<UserData?> = _currentUserData

    override suspend fun getUserData(uid: String): Result<UserData> {
        return try {
            val entity = userService.getUserData(uid)
            Result.success(entity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUserId(): String? {
        return userService.getCurrentUserId()
    }

    override suspend fun setCurrentUserData(userData: UserData) {
        _currentUserData.value = userData
    }

    override fun clearCurrentUserData() {
        _currentUserData.value = null
    }
}