package com.packt.whatspackt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.packt.chat.ui.ChatScreen
import com.packt.conversations.ui.ConversationListScreen
import com.packt.create_chat.presentation.ui.CreateChatScreen
import com.packt.framework.navigation.DeepLinks
import com.packt.framework.navigation.NavRoutes
import com.packt.login.ui.LoginScreen
import com.packt.login.ui.RegisterScreen
import com.packt.login.ui.viewmodel.LogInViewModel
import com.packt.onboarding.ui.OnboardingPage
import com.packt.onboarding.ui.OnboardingScreen
import com.packt.splash.ui.SplashScreen

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoutes.SplashScreen) {
        addSplashScreen(navController)
        addOnboardingNavGraph(navController) {
            navController.navigate(NavRoutes.LogInScreen) {
                popUpTo(NavRoutes.Onboarding) { inclusive = true }
            }
        }
        addLogInScreen(navController)
        addRegisterScreen(navController)
        addConversationList(navController)
        addNewConversation(navController)
        addChat(navController)
    }
}

private fun NavGraphBuilder.addSplashScreen(navController: NavHostController) {
    composable(route = NavRoutes.SplashScreen) {
        SplashScreen {
            navController.popBackStack() // Remove Splash from back stack
            navController.navigate(NavRoutes.Onboarding) // Navigate to Onboarding first
        }
    }
}

fun NavGraphBuilder.addOnboardingNavGraph(navController: NavHostController, onDone: () -> Unit) {
    composable(route = NavRoutes.Onboarding) {
        val samplePages = listOf(
            OnboardingPage(
                title = "Welcome to WhatsPackt",
                description = "Your go-to messaging app with awesome features."
            ),
            OnboardingPage(
                title = "Stay Connected",
                description = "Chat with friends and family anytime, anywhere."
            ),
            OnboardingPage(
                title = "Secure & Private",
                description = "Your conversations are safe with end-to-end encryption."
            )
        )
        OnboardingScreen(
            onboardingPages = samplePages,
            onGetStarted = onDone
        )
    }
}

private fun NavGraphBuilder.addLogInScreen(navController: NavHostController) {
    composable(route = NavRoutes.LogInScreen) {
        val viewModel: LogInViewModel = hiltViewModel() // or obtain your ViewModel instance here

        LoginScreen(
            onLoginSuccess = {
                navController.navigate(NavRoutes.ConversationsList) {
                    popUpTo(NavRoutes.LogInScreen) { inclusive = true }
                }
            },
            onSignUp = { navController.navigate(NavRoutes.RegisterScreen) },
            onLogin = { username, password ->
                viewModel.login(username, password)  // Call the ViewModel login function
            }
        )
    }
}

private fun NavGraphBuilder.addRegisterScreen(navController: NavHostController) {
    composable(route = NavRoutes.RegisterScreen) {
        RegisterScreen()
    }
}

private fun NavGraphBuilder.addConversationList(navController: NavHostController) {
    composable(route = NavRoutes.ConversationsList) {
        ConversationListScreen(
            onNewConversationClick = {
                navController.navigate(NavRoutes.NewConversation)
            },
            onConversationClick = { chatId ->
                navController.navigate(NavRoutes.Chat.replace("{chatId}", chatId))
            }
        )
    }
}

private fun NavGraphBuilder.addNewConversation(navController: NavHostController) {
    composable(route = NavRoutes.NewConversation) {
        CreateChatScreen(onStartChat = { chatId ->
            navController.navigate(NavRoutes.Chat.replace("{chatId}", chatId))
        })
    }
}

private fun NavGraphBuilder.addChat(navController: NavHostController) {
    composable(
        route = NavRoutes.Chat,
        arguments = listOf(
            navArgument(NavRoutes.ChatArgs.ChatId) { type = NavType.StringType }
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = DeepLinks.chatRoute }
        )
    ) { backStackEntry ->
        val chatId = backStackEntry.arguments?.getString(NavRoutes.ChatArgs.ChatId)
        ChatScreen(chatId = chatId ?: "", onBack = {
            navController.popBackStack()
        })
    }
}