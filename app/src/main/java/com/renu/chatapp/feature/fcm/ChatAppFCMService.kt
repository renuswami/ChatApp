package com.renu.chatapp.feature.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.renu.chatapp.BuildConfig
import com.renu.chatapp.MainActivity
import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.domain.model.ext.id
import com.streamliners.base.exception.defaultExecuteHandlingError
import com.streamliners.helpers.NotificationHelper
import org.koin.android.ext.android.inject

class ChatAppFCMService : FirebaseMessagingService() {

    private val userRepo: UserRepo by inject()
    private val localRepo: LocalRepo by inject()

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        logData(data)

        val notificationParams = message.notification ?: return
        val title = notificationParams.title ?: return
        val body = notificationParams.body ?: return

        showNotification(title, body)
    }

    private fun logData(data: Map<String, String>) {
        data.forEach { (key, value) ->
            Log.i("ChatAppDebug", "message.data recived : $key : $value")
        }
    }

    private fun showNotification(title: String, body: String) {
        NotificationHelper(this)
            .showNotification(
                title = title,
                body = body,
                pendingIntentActivity = MainActivity::class.java
            )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        defaultExecuteHandlingError(
            lambda = {
                if(localRepo.isLoggedIn()){
                    val user = localRepo.getLoggedInUser().copy(fcmToken = token)
                    localRepo.upsertCurrentUser(user)
                    userRepo.updateFcmToken(token, user.id())
                }
            },
            buildType = BuildConfig.BUILD_TYPE
        )
    }
}