package com.packt.framework.navigation

object NavRoutes {

    const val SplashScreen = "splash_screen"
    const val Onboarding = "onboarding_screen"
    const val LogInScreen = "log_in_screen"
    const val RegisterScreen = "register_screen"
    const val ConversationsList = "conversations_list"
    const val Profile = "profile"
    const val NewConversation = "create_conversation"
    const val Chat = "chats/{chatId}"

    object ChatArgs {
        const val ChatId = "chatId"
    }

    object ProfileArgs {
        const val UserId = "userId"
    }

}