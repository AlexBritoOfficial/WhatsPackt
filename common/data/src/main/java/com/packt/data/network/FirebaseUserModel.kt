package com.packt.data.network

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import com.packt.data.model.UserDataEntity
import com.packt.domain.model.UserData

data class FirestoreUserModel(

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
) {

    fun toEntity(): UserDataEntity {
        return UserDataEntity(
            id = id,
            avatarUrl = avatarUrl,
            displayName = displayName,
            lastActive = lastActive, // Keep Timestamp
            status = status,
            username = username,
            phone = phone,
            bio = bio,
            createdAt = createdAt,   // Keep Timestamp
            email = email,
            searchKeywords = searchKeywords
        )
    }

    companion object {
        fun fromDomain(domain: UserData): FirestoreUserModel {
            return FirestoreUserModel(
                id = domain.id,
                avatarUrl = domain.avatarUrl,
                displayName = domain.displayName,
                lastActive = Timestamp(domain.lastActive, 0),
                status = domain.status,
                username = domain.username,
                phone = domain.phone,
                bio = domain.bio,
                createdAt = Timestamp(domain.createdAt, 0),
                email = domain.email,
                searchKeywords = domain.searchKeywords
            )
        }
    }
}