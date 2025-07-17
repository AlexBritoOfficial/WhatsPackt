package com.packt.data


import com.google.firebase.Timestamp
import com.packt.data.model.UserDataEntity
import com.packt.domain.model.UserData

fun UserDataEntity.toDomain(): UserData = UserData(
    id = id,
    avatarUrl = avatarUrl,
    displayName = displayName,
    lastActive = lastActive.seconds,
    status = status,
    username = username,
    phone = phone,
    bio = bio,
    createdAt = createdAt.seconds,
    email = email,
    searchKeywords = searchKeywords
)

fun UserData.toEntity(): UserDataEntity {
    return UserDataEntity(
        id = id,
        avatarUrl = avatarUrl,
        displayName = displayName,
        lastActive = Timestamp(lastActive, 0),
        status = status,
        username = username,
        phone = phone,
        bio = bio,
        createdAt = Timestamp(createdAt, 0),
        email = email,
        searchKeywords = searchKeywords
    )
}