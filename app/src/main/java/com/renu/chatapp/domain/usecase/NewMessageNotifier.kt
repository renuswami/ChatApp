package com.renu.chatapp.domain.usecase

import com.renu.chatapp.data.remote.OtherRepo
import com.renu.chatapp.helper.fcm.AndroidPayload
import com.renu.chatapp.helper.fcm.FcmMessage
import com.renu.chatapp.helper.fcm.FcmPayload
import com.renu.chatapp.helper.fcm.FcmSender
import com.renu.chatapp.helper.fcm.NotificationPayload

class NewMessageNotifier(
    private val otherRepo: OtherRepo,
    private val fcmSender: FcmSender
) {
    suspend fun notify() {
        val svcAcJson = otherRepo.getServiceAccountJson()
        val payload = FcmPayload(
            FcmMessage.forTopic(
                topic = "general",
                notification = NotificationPayload(
                    title = "Sample notification",
                    body = "This notification was sent from App using ktor and FCM REST API"
                ),
                android = AndroidPayload(
                    priority = "high"
                )
            )
        )
        fcmSender.send(payload, svcAcJson)
    }
}
