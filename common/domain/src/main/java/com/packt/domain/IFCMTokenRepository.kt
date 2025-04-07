package com.packt.domain

interface IFCMTokenRepository {
    suspend fun getFCMToken(): String?
}