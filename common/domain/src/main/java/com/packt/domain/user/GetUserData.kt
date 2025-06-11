package com.packt.domain.user

import com.google.firebase.Timestamp
import javax.inject.Inject


class GetUserData @Inject constructor () {

    fun getData(): UserData{
        // User Id will be passed as a parameter and it will Firebase Document ID
        return UserData("fcELsPmQgLEowVW7iiZg",
            "https://example.com/avatar2.jpg",
            "Alex Brito", Timestamp.now(),
            "Living resiliently",
            "alex_brito")
    }
}