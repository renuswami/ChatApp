package com.renu.chatapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.renu.chatapp.temp.Scripts
import com.renu.chatapp.ui.CharAppNavHost
import com.renu.chatapp.ui.theme.ChatAppTheme
import com.streamliners.base.BaseActivity
import com.streamliners.base.uiEvent.UiEventDialogs

class MainActivity : BaseActivity() {

    //TODO : Improve navigaation using popUpTo

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
        runScripts()
    }

    private fun runScripts() {
        execute {
            Scripts.saveDummyChannel()
            Scripts.channelQueryTest()
         //   Scripts.saveDummyUsers()
        }
    }
}

