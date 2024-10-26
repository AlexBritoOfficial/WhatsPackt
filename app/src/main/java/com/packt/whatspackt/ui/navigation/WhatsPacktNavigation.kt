package com.packt.whatspackt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.packt.chat.ui.ChatScreen
import com.packt.conversations.ui.ConversationListScreen
import com.packt.create_chat.CreateConversationScreen
import com.packt.framework.navigation.NavRoutes

@Composable
fun MainNavigation(navController: NavHostController) {


    NavHost(navController = navController, startDestination = "chat/5 ") {

//        addConversationList(navController)
//        addNewConversation(navController)
        addChat(navController)
    }
}

private fun NavGraphBuilder.addConversationList(navController: NavHostController) {

    composable(route = NavRoutes.ConversationsList) {
        ConversationListScreen(onNewConversationClick = {
            navController.navigate(NavRoutes.NewConversation)
        }, onConversationClick = { chatId ->
            navController.navigate(NavRoutes.Chat.replace("{chatId}", chatId))
        })
    }

}

private fun NavGraphBuilder.addNewConversation(navController: NavHostController) {

    composable(route = NavRoutes.NewConversation) {
        CreateConversationScreen(onCreateConversation = {
            navController.navigate(NavRoutes.Chat)
        })
    }

}

private fun NavGraphBuilder.addChat(navController: NavHostController) {

    composable(
        route = NavRoutes.Chat,
        arguments = listOf(navArgument(NavRoutes.ChatArgs.ChatId) {
            type = NavType.StringType
        })
    ) {
        backStackEntry ->

        val chatId = backStackEntry.arguments?.getString(NavRoutes.ChatArgs.ChatId)

        ChatScreen(chatId = "5", onBack = {
            navController.popBackStack()
        })
    }
}