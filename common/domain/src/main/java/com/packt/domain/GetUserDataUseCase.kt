package com.packt.domain

import com.packt.domain.model.UserData
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uid: String): Result<UserData> {
        return userRepository.getUserData(uid)
    }
}