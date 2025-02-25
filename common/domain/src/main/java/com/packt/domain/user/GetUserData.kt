package com.packt.domain.user

import javax.inject.Inject

class GetUserData @Inject constructor () {

    fun getData(): UserData{
        return UserData("1", "Alex Brito", "")
    }
}