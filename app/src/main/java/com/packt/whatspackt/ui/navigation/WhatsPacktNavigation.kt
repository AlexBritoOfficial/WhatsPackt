package com.packt.whatspackt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
import com.packt.conversations.ui.ConversationsViewModel
import com.packt.create_chat.presentation.ui.CreateChatScreen
import com.packt.domain.model.AuthStatus
import com.packt.framework.navigation.DeepLinks
import com.packt.framework.navigation.LastRouteDataStore
import com.packt.framework.navigation.NavRoutes
import com.packt.login.ui.LoginScreen
import com.packt.login.ui.RegisterScreen
import com.packt.login.ui.viewmodel.LogInViewModel
import com.packt.onboarding.ui.OnboardingPage
import com.packt.onboarding.ui.OnboardingScreen
import com.packt.profile.ProfileScreen
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
        addCreateProfile(navController)
        addNewConversation(navController)
        addChat(navController)
    }
}

private fun NavGraphBuilder.addSplashScreen(navController: NavHostController) {
    composable(route = NavRoutes.SplashScreen) {
        val context = LocalContext.current
        val lastRoute by LastRouteDataStore.getLastRoute(context).collectAsState(initial = null)

        SplashScreen {
            navController.popBackStack()
            if (lastRoute.isNullOrEmpty()) {
                navController.navigate(NavRoutes.Onboarding)
            } else {
                navController.navigate(lastRoute!!)
            }
        }
    }
}

fun NavGraphBuilder.addOnboardingNavGraph(navController: NavHostController, onDone: () -> Unit) {
    composable(route = NavRoutes.Onboarding) {
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            LastRouteDataStore.saveLastRoute(context, NavRoutes.Onboarding)
        }

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
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            LastRouteDataStore.saveLastRoute(context, NavRoutes.LogInScreen)
        }

        val viewModel: LogInViewModel = hiltViewModel()

        LoginScreen(
            onLoginSuccess = {
                navController.navigate(NavRoutes.ConversationsList) {
                    popUpTo(0) // Clears entire back stack
                    launchSingleTop = true
                }
            },
            onSignUp = { navController.navigate(NavRoutes.RegisterScreen) },
            onLogin = { username, password ->
                viewModel.login(username, password)
            }
        )
    }
}

private fun NavGraphBuilder.addRegisterScreen(navController: NavHostController) {
    composable(route = NavRoutes.RegisterScreen) {
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            LastRouteDataStore.saveLastRoute(context, NavRoutes.RegisterScreen)
        }

        RegisterScreen()
    }
}

private fun NavGraphBuilder.addConversationList(navController: NavHostController) {
    composable(route = NavRoutes.ConversationsList) {
        val context = LocalContext.current
        val viewModel: ConversationsViewModel = hiltViewModel()

        // Observe auth state from the ViewModel
        val authStatus by viewModel.authState.collectAsState()

        // Save the last visited route
        LaunchedEffect(Unit) {
            LastRouteDataStore.saveLastRoute(context, NavRoutes.ConversationsList)
        }

        // Handle logout state by navigating to the login screen
        LaunchedEffect(authStatus) {
            if (authStatus == AuthStatus.Unauthenticated) {
                navController.navigate(NavRoutes.LogInScreen) {
                    popUpTo(NavRoutes.ConversationsList) { inclusive = true }
                }
            }
        }

        // UI for the conversation list screen
        ConversationListScreen(
            onNewConversationClick = {
                navController.navigate(NavRoutes.NewConversation)
            },
            onConversationClick = { chatId ->
                navController.navigate(NavRoutes.Chat.replace("{chatId}", chatId))
            },
            onProfileClick = {
                navController.navigate(NavRoutes.Profile)
            }
        )
    }
}


private fun NavGraphBuilder.addCreateProfile(navController: NavHostController) {
    composable(route = NavRoutes.Profile) {
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            LastRouteDataStore.saveLastRoute(context, NavRoutes.Profile)
        }

        ProfileScreen()
    }
}

private fun NavGraphBuilder.addNewConversation(navController: NavHostController) {
    composable(route = NavRoutes.NewConversation) {
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            LastRouteDataStore.saveLastRoute(context, NavRoutes.NewConversation)
        }

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

        val context = LocalContext.current

        LaunchedEffect(chatId) {
            if (!chatId.isNullOrEmpty()) {
                val resolvedRoute = NavRoutes.Chat.replace("{chatId}", chatId)
                LastRouteDataStore.saveLastRoute(context, resolvedRoute)
            }
        }
        ChatScreen(chatId = chatId ?: "", onBack = {
            navController.popBackStack()
        })
    }
}
