package com.renu.chatapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.renu.chatapp.MainActivity
import com.renu.chatapp.feature.chat.ChatScreen
import com.renu.chatapp.feature.editProfile.EditProfileScreen
import com.renu.chatapp.feature.home.HomeScreen
import com.renu.chatapp.feature.login.LoginScreen
import com.renu.chatapp.feature.newChat.NewChatScreen
import com.renu.chatapp.feature.splash.SplashScreen
import com.streamliners.base.ext.koinBaseViewModel
import com.streamliners.pickers.date.showDatePickerDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainActivity.ChatAppNavHost() {

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Screen.Spalsh.route
    ) {
        composable(Screen.Spalsh.route) {
            SplashScreen(navController, koinViewModel())
        }

        composable(Screen.Login.route) {
            LoginScreen(navController, koinBaseViewModel())
        }

        composable(
            Screen.EditProfile.format(),
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                }
            )
        ) {
            val email = it.arguments?.getString("email")?: error("Email argument not passed")
            EditProfileScreen(
                navController = navController,
                viewModel = koinBaseViewModel(),
                email = email,
                showDatePicker = ::showDatePickerDialog,
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                navController,
                koinBaseViewModel()
            )
        }

        composable(Screen.NewChat.route) {
            NewChatScreen(
                viewModel = koinBaseViewModel(),
                navController = navController
            )
        }

        composable(
            Screen.Chat.format(),
            arguments = listOf(
                navArgument("channelId") {
                    type = NavType.StringType
                }
            )
        ) {
            val channelId = it.arguments?.getString("channelId")?: error("channelId argument not passed")
            ChatScreen(
                channelId = channelId,
                navController = navController,
                viewModel = koinBaseViewModel()
            )
        }
    }
}