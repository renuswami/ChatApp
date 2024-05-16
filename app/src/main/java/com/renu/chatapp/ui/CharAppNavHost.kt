package com.renu.chatapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.renu.chatapp.MainActivity
import com.renu.chatapp.feature.editProfile.EditProfileScreen
import com.renu.chatapp.feature.home.HomeScreen
import com.renu.chatapp.feature.login.LoginScreen
import com.renu.chatapp.feature.splash.SplashScreen
import com.streamliners.pickers.date.showDatePickerDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainActivity.CharAppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Spalsh.route
    ) {
        composable(Screen.Spalsh.route) {
            SplashScreen(navController, koinViewModel())
        }

        composable(Screen.Login.route) {
            LoginScreen(navController, koinViewModel())
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
                viewModel = koinViewModel(),
                email = email,
                showDatePicker = ::showDatePickerDialog
            )
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }

    }
}