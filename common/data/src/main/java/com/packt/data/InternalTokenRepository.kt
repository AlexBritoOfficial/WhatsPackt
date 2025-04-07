package com.packt.data

import com.packt.domain.IInternalTokenRepository
import javax.inject.Inject

class InternalTokenRepository() : IInternalTokenRepository {
    override suspend fun storeFCMToken(userId: String, token: String) {
            // Store in the data source of your choosing
    }
}