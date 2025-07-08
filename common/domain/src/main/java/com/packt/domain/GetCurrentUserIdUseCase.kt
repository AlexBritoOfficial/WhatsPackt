package com.packt.domain

import com.packt.domain.user.UserRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String {
        return userRepository.getCurrentUserId().toString()
    }
}
