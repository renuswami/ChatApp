package com.renu.chatapp.ui

import android.provider.ContactsContract.CommonDataKinds.Email

sealed class Screen(
    val route: String
) {
    data object Spalsh : Screen("Spalsh")
    data object Login : Screen("Login")

    data object Home : Screen("Home")
    class EditProfile(
        email: String
    ) : Screen("EditProfile?email=$email"){
        companion object{
            fun format() = "EditProfile?email={email}"
        }
    }
}

