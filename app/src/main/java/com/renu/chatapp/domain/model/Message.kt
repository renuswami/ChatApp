package com.renu.chatapp.domain.model

import android.content.IntentSender
import com.google.firebase.Timestamp
import java.lang.reflect.Constructor


data class Message(
    val time: Timestamp = Timestamp.now(),
    // UserId of the sender
    val sender: String,
    val message: String,
    val mediaUrl: String?,
) {
    constructor() : this(Timestamp.now(), "","", null)
}
