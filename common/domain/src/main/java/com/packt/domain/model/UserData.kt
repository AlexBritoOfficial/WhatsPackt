package com.packt.domain.model

data class UserData(
    val id: String,
    val avatarUrl: String,
    val displayName: String,
    val lastActive: Long,
    val status: String,
    val username: String,
    val phone: String,
    val bio: String,
    val createdAt: Long,
    val email: String,
    val searchKeywords: List<String>
)