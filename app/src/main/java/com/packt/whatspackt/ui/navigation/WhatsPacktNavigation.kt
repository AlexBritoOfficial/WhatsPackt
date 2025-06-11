package com.packt.whatspackt.ui.navigation

import StartNewConversationScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.packt.chat.ui.ChatScreen
import com.packt.conversations.ui.ConversationListScreen
import com.packt.framework.navigation.DeepLinks
import com.packt.framework.navigation.NavRoutes

@Composable
fun MainNavigation(navController: NavHostController) {


    NavHost(navController = navController, startDestination = NavRoutes.ConversationsList) {

        addConversationList(navController)
        addNewConversation(navController)
        addChat(navController)

    }
}

private fun NavGraphBuilder.addConversationList(navController: NavHostController) {

    composable(route = NavRoutes.ConversationsList) {
        ConversationListScreen(onNewConversationClick = {
            navController.navigate(NavRoutes.NewConversation)
        }, onConversationClick = { chatId ->
            navController.navigate(NavRoutes.Chat.replace("{chatId}", "chatId${chatId}"))
        })
    }

}

private fun NavGraphBuilder.addNewConversation(navController: NavHostController) {

    composable(route = NavRoutes.NewConversation) {
        StartNewConversationScreen()
    }

}

private fun NavGraphBuilder.addChat(navController: NavHostController) {

    composable(
        route = NavRoutes.Chat,
        arguments = listOf(navArgument(NavRoutes.ChatArgs.ChatId) { type = NavType.StringType }),
        deepLinks = listOf(navDeepLink { uriPattern = DeepLinks.chatRoute })
    ) { backStackEntry ->

        val chatId = backStackEntry.arguments?.getString(NavRoutes.ChatArgs.ChatId)

        ChatScreen(chatId = chatId, onBack = {
            navController.popBackStack()
        })
    }
}