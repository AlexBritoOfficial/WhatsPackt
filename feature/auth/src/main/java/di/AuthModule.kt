package di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.network.FirebaseAuthService
import data.repository.AuthRepositoryImpl
import domain.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthService(auth: FirebaseAuth, firestore: FirebaseFirestore): FirebaseAuthService =
        FirebaseAuthService(auth, firestore)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: FirebaseAuthService,
        firestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(authService, firestore)
}