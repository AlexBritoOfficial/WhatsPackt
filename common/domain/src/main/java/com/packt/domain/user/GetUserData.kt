package com.packt.domain.user

import javax.inject.Inject


class GetUserData @Inject constructor () {

    fun getData(): UserData{
        return UserData("user1", "John Doe", "https://example.com/avatar1.jpg")
    }
}