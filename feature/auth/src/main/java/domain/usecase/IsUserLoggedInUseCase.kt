package domain.usecase

import domain.AuthRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}