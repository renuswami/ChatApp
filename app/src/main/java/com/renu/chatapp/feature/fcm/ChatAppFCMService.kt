package com.renu.chatapp.feature.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.renu.chatapp.MainActivity
import com.streamliners.helpers.NotificationHelper

class ChatAppFCMService: FirebaseMessagingService() {

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
            Log.i("ChatAppDebug","message.data recived : $key : $value")
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
}