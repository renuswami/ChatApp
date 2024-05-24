package com.renu.chatapp.helper

import androidx.navigation.NavController
import com.firebase.ui.auth.ui.phone.CountryListSpinner.DialogPopup
import com.renu.chatapp.ui.Screen

fun NavController.navigate(
    to: Screen,
    popUpTo: Screen? = null,
    inclusive: Boolean = true
){
    if (popUpTo != null){ popBackStack(popUpTo.route, inclusive) }
    navigate(to.route)
}

fun NavController.navigate(
    to: String,
    popUpTo: String? = null,
    inclusive: Boolean = true
){
    if (popUpTo != null){ popBackStack(popUpTo, inclusive) }
    navigate(to)
}