package com.packt.domain.model

sealed class AuthStatus {
    object Authenticated: AuthStatus()
    object Unauthenticated: AuthStatus()
    object Loading: AuthStatus()
}