package domain.usecase

import com.packt.domain.user.UserData
import domain.AuthRepository
import javax.inject.Inject

class LoginWithUsernameUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<UserData> {
        return authRepository.loginWithUsername(username, password)
    }
}
