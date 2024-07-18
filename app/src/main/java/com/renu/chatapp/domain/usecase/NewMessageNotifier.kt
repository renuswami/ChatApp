package com.renu.chatapp.domain.usecase

import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.data.remote.OtherRepo
import com.renu.chatapp.domain.model.User
import com.renu.chatapp.domain.model.ext.id
import com.renu.chatapp.helper.fcm.FcmMessage
import com.renu.chatapp.helper.fcm.FcmPayload
import com.renu.chatapp.helper.fcm.FcmSender

class NewMessageNotifier(
    private val userRepo: UserRepo,
    private val otherRepo: OtherRepo,
    private val fcmSender: FcmSender
) {
    suspend fun notifySingleUser(
        sender: User,
        userId: String,
        message: String
    ) {
        val token = userRepo.getUserById(userId).fcmToken ?: return

        val payload = FcmPayload(
            FcmMessage.forToken(
                token  = token,
                data = mapOf(
                    "title" to "New Message",
                    "body" to "${sender.name} : $message"
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
                data = mapOf(
                    "title" to "New Message",
                    "body" to "${sender.name} : $message",
                    "sendUserId" to sender.id()
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
