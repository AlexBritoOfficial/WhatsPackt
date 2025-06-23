package domain.usecase

import domain.AuthRepository
import javax.inject.Inject


class RegisterWithEmailUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String, username: String): Result<Boolean> {
        return authRepository.registerWithEmail(email, password, username = username)
    }
}