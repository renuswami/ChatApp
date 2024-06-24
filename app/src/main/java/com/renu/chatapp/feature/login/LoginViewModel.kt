package com.renu.chatapp.feature.login

import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.domain.model.ext.id
import com.renu.chatapp.helper.navigate
import com.renu.chatapp.ui.Screen
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.ext.executeOnMain
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val localRepo: LocalRepo
): BaseViewModel() {

    fun onLoggedIn(email: String, navController: NavHostController) {
        execute {
            var user = userRepo.getUserWithEmail(email)

            Firebase.messaging.subscribeToTopic("general").await()
            if (user != null){
                // Update FCM Token
                val token = Firebase.messaging.token.await()
                user = user.copy(fcmToken = token)
                userRepo.updateFcmToken(token, user.id())

                // Save in local & navigate to HomeSceen
                localRepo.upsertCurrentUser(user)
                executeOnMain { navController.navigate(Screen.Home.route)
                }
            } else {
                // Navigate to  EditProfileScreen
                executeOnMain { navController.navigate(Screen.EditProfile(email), Screen.Login) }
            }
        }
    }
}