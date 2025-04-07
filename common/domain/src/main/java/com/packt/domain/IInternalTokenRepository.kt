package com.packt.domain

interface IInternalTokenRepository {

    suspend fun storeFCMToken(userId:String, token:String)
}