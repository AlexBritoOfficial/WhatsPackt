package domain.usecase

import domain.AuthRepository
import javax.inject.Inject

class LoginWithUsernameUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<String> {
        return authRepository.loginWithUsername(username, password)
    }
}
