package com.renu.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.renu.chatapp.feature.editProfile.EditProfileScreen
import com.renu.chatapp.feature.login.LoginScreen
import com.renu.chatapp.feature.splash.SplashScreen
import com.renu.chatapp.temp.Scripts
import com.renu.chatapp.ui.CharAppNavHost
import com.renu.chatapp.ui.Screen
import com.renu.chatapp.ui.theme.ChatAppTheme
import com.streamliners.base.BaseActivity
import com.streamliners.base.uiEvent.UiEventDialogs

class MainActivity : BaseActivity() {

    override var buildType: String = BuildConfig.BUILD_TYPE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CharAppNavHost()
                    UiEventDialogs()
                }
            }
        }
          // runScripts()
    }

    private fun runScripts() {
        execute {
            Scripts.saveDummyUsers()
        }
    }
}

