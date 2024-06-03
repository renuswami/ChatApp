package com.renu.chatapp.feature.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.renu.chatapp.MainActivity
import com.streamliners.helpers.NotificationHelper

class ChatAppFCMService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationParams = message.notification ?: return
        val title = notificationParams.title ?: return
        val body = notificationParams.body ?: return

        showNotification(title, body)
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