package com.packt.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class AppUser(
    @PrimaryKey val userId: String, // Firestore document ID
    val avatarUrl: String = "",
    val displayName: String = "",
    val lastActive: Long = 0L, // Store as epoch millis
    val phone: String = "",
    val status: String = "",
    val username: String = ""
)