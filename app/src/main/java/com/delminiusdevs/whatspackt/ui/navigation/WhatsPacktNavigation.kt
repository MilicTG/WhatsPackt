package com.delminiusdevs.whatspackt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.delminiusdevs.chat.ui.ChatScreen
import com.delminiusdevs.conversations.ConversationsListScreen
import com.delminiusdevs.create_chat.CreateConversationScreen
import com.delminiusdevs.framework.navigation.DeepLinks
import com.delminiusdevs.framework.navigation.NavRoutes


@Composable
fun MainNavigation(navController: NavHostController) {

    NavHost(navController, startDestination = NavRoutes.ConversationsList) {

        addConversationsList(navController)

        addNewConversation(navController)

        addChat(navController)

    }
}

private fun NavGraphBuilder.addConversationsList(navController: NavHostController) {

    composable(NavRoutes.ConversationsList) {
        ConversationsListScreen(
            onNewConversationClick = { navController.navigate(NavRoutes.NewConversation) },
            onConversationClick = { chatId ->
                navController.navigate(NavRoutes.Chat.replace("{chatId}", chatId))
            }
        )
    }

}

private fun NavGraphBuilder.addNewConversation(navController: NavHostController) {
    composable(NavRoutes.NewConversation) {
        CreateConversationScreen(onCreateConversation = { navController.navigate(NavRoutes.Chat) })
    }
}

private fun NavGraphBuilder.addChat(navController: NavHostController) {

    composable(
        route = NavRoutes.Chat,
        arguments = listOf(navArgument(NavRoutes.ChatArgs.ChatId) { type = NavType.StringType }),
        deepLinks = listOf(navDeepLink { uriPattern = DeepLinks.chatRoute })
    ) { backStackEntry ->
        val chatId = backStackEntry.arguments?.getString(NavRoutes.ChatArgs.ChatId)
        chatId?.let { ChatScreen(chatId = it, onBack = { navController.popBackStack() }) }
    }
}
