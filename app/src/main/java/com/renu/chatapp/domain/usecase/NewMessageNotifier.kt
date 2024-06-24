package com.renu.chatapp.domain.usecase

import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.data.remote.OtherRepo
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
    suspend fun notify(
        senderName: String,
        userId: String,
        message: String
    ) {
        val token = userRepo.getUserById(userId).fcmToken ?: return
        val svcAcJson = otherRepo.getServiceAccountJson()


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
        fcmSender.send(payload, svcAcJson)
    }
}
