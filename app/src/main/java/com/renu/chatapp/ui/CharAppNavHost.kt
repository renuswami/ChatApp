package com.renu.chatapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.renu.chatapp.feature.editProfile.EditProfileScreen
import com.renu.chatapp.feature.login.LoginScreen
import com.renu.chatapp.feature.splash.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun CharAppNavHost() {

    val navController = rememberNavController()

    LaunchedEffect(key1 = ""){
        delay(100)
        navController.navigate(
            Screen.EditProfile("renuswami2001@gmail.com").route
        )
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Spalsh.route
    ) {
        composable(Screen.Spalsh.route) {
            SplashScreen()
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
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
            EditProfileScreen(viewModel(), email)
        }
    }
}