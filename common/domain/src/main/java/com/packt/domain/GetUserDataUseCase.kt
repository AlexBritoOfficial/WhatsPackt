package com.packt.domain

import com.packt.domain.user.UserData
import com.packt.domain.user.UserRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uid: String): Result<UserData> {
        return userRepository.getUserData(uid)
    }
}