package domain.usecase

import domain.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke() {
        authRepository.logout()
    }
}