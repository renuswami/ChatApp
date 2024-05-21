package com.renu.chatapp.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.renu.chatapp.R
import com.renu.chatapp.ui.theme.Graident


@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel
) {
    LaunchedEffect(Unit){ viewModel.checkLoginStatus(navController) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Graident),
        contentAlignment = Alignment.Center

    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.ic_chat),
            contentDescription = "Chat",
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}