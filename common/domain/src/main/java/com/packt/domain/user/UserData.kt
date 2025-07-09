package com.packt.domain.user

import com.google.firebase.Timestamp


data class UserData(
    val id: String,
    val avatarUrl: String,
    val displayName: String,
    val lastActive: Timestamp,
    val status: String,
    val username: String
)
