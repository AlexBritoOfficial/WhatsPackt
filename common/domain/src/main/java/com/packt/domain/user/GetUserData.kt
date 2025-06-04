package com.packt.domain.user

import javax.inject.Inject


class GetUserData @Inject constructor () {

    fun getData(): UserData{
        return UserData("user123", "Alex Brito", "https://example.com/avatar2.jpg")
    }
}