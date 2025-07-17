package com.packt.domain

import com.packt.domain.model.AuthStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAuthStatusUseCase @Inject constructor (private val userRepository: UserRepository) {
    suspend operator fun invoke(): Flow<AuthStatus> {
        return userRepository.observeAuthStatus()
    }
}