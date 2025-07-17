package com.packt.domain

import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(private val repository: UserRepository){
    suspend operator fun invoke(){
        repository.logout()
    }

}