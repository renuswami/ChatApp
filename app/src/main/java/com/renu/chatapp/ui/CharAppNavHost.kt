package com.renu.chatapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.renu.chatapp.feature.editProfile.EditProfileScreen
import com.renu.chatapp.feature.login.LoginScreen
import com.renu.chatapp.feature.splash.SplashScreen

@Composable
fun CharAppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.name
    ) {
        composable(Screen.Spalsh.name) {
            SplashScreen()
        }

        composable(Screen.Login.name) {
            LoginScreen(navController)
        }

        composable(
            "EditProfile?email={email}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                }
            )
        ) {
            val email = it.arguments?.getString("email")?: error("Email argument not passed")
            EditProfileScreen(email)
        }
    }
}