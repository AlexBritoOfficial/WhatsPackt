package com.packt.domain

import com.packt.domain.model.UserData
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userData: UserData): Result<Unit> {
        return userRepository.updateUserData(userData = userData)
    }
}