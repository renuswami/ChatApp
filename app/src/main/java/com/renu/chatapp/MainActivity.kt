package com.renu.chatapp

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.renu.chatapp.temp.Scripts
import com.renu.chatapp.ui.ChatAppNavHost
import com.renu.chatapp.ui.theme.ChatAppTheme
import com.streamliners.base.BaseActivity
import com.streamliners.base.uiEvent.UiEventDialogs
import kotlinx.coroutines.tasks.await

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
                    ChatAppNavHost()
                    UiEventDialogs()
                }
            }
        }

        /* execute {
            val token = Firebase.messaging.token.await()
            Log.i("ChatAppDebug", "FCM token: $token")
        }*/
        // runScripts()
    }

    private fun runScripts() {
        execute {
            Scripts.saveDummyChannel()
            Scripts.channelQueryTest()
           // Scripts.saveDummyUsers()
        }
    }
}

