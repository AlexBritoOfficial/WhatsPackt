package com.packt.domain

import com.packt.domain.user.UserData
import com.packt.domain.user.UserRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userData: UserData): Result<Unit> {
        return userRepository.updateUserData(userData = userData)
    }
}