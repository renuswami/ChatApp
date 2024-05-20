package com.renu.chatapp.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.ui.Screen
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val localRepo: LocalRepo
): ViewModel(){

    fun onLoggedIn(email: String, navController: NavHostController) {
        viewModelScope.launch {
            val user = userRepo.getUserWithEmail(email)
            if (user != null){
                // Save in local & navigate to HomeSceen
                localRepo.onLoggedIn(user)
                navController.navigate(Screen.Home.route)
            } else {
                // Navigate to  EditProfileScreen
                navController.navigate(Screen.EditProfile(email).route)
            }
        }
    }
}