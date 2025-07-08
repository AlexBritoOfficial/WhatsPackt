package com.packt.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class UserDataEntity(
    @get: PropertyName("userId")
    @set: PropertyName("userId")
    var id: String = "",

    @get: PropertyName("avatarUrl")
    @set: PropertyName("avatarUrl")
    var avatarUrl: String = "",

    @get: PropertyName("displayName")
    @set: PropertyName("displayName")
    var displayName: String = "",

    @get: PropertyName("lastActive")
    @set: PropertyName("lastActive")
    var lastActive: Timestamp = Timestamp.now(),

    @get: PropertyName("status")
    @set: PropertyName("status")
    var status: String = "",

    @get: PropertyName("username")
    @set: PropertyName("username")
    var username: String = "",

    @get: PropertyName("phone")
    @set: PropertyName("phone")
    var phone: String = "",

    @get: PropertyName("bio")
    @set: PropertyName("bio")
    var bio: String = "",

    @get: PropertyName("createdAt")
    @set: PropertyName("createdAt")
    var createdAt: Timestamp = Timestamp.now(),

    @get: PropertyName("email")
    @set: PropertyName("email")
    var email: String = "",

    @get: PropertyName("searchKeywords")
    @set: PropertyName("searchKeywords")
    var searchKeywords: List<String> = emptyList()
)