package com.renu.chatapp.domain.usecase

import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.data.remote.OtherRepo
import com.renu.chatapp.domain.model.User
import com.renu.chatapp.domain.model.ext.id
import com.renu.chatapp.helper.fcm.AndroidPayload
import com.renu.chatapp.helper.fcm.FcmMessage
import com.renu.chatapp.helper.fcm.FcmPayload
import com.renu.chatapp.helper.fcm.FcmSender
import com.renu.chatapp.helper.fcm.NotificationPayload

class NewMessageNotifier(
    private val userRepo: UserRepo,
    private val otherRepo: OtherRepo,
    private val fcmSender: FcmSender
) {
    suspend fun notifySingleUser(
        senderName: String,
        userId: String,
        message: String
    ) {
        val token = userRepo.getUserById(userId).fcmToken ?: return

        val payload = FcmPayload(
            FcmMessage.forToken(
                token  = token,
                notification = NotificationPayload(
                    title = "New Message",
                    body = "$senderName : $message"
                ),
                android = AndroidPayload(
                    priority = "high"
                )
            )
        )
        sendNotification(payload)
    }

    suspend fun notifyMultipleUserUsingTopic(
        sender: User,
        topic: String,
        message: String
    ) {
        val payload = FcmPayload(
            FcmMessage.forTopic(
                topic = topic,
                notification = NotificationPayload(
                    title = "New Message",
                    body = "${sender.name} : $message"
                ),
                data = mapOf(
                    "sendUserId" to sender.id()
                ),
                android = AndroidPayload(
                    priority = "high"
                )
            )
        )
        sendNotification(payload)
    }

    private suspend fun sendNotification(payload: FcmPayload) {
        val svcAcJson = otherRepo.getServiceAccountJson()
        fcmSender.send(payload, svcAcJson)
    }
}
