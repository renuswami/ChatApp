package com.renu.chatapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.renu.chatapp.feature.editProfile.EditProfileScreen
import com.renu.chatapp.feature.login.LoginScreen
import com.renu.chatapp.feature.splash.SplashScreen

@Composable
fun CharAppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.EditProfile.name
    ){
        composable(Screen.Spalsh.name){
            SplashScreen()
        }

        composable(Screen.Login.name){
            LoginScreen()
        }

        composable(Screen.EditProfile.name){
            EditProfileScreen()
        }
    }
}