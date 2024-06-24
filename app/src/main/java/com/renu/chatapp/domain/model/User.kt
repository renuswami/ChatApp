package com.renu.chatapp.domain.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val id: String? = null,
    val name: String,
    val email: String,
    val profileImageUrl: String?,
    val bio: String,
    val gender: Gender,
    val dob: String?,
    val fcmToken: String?
){
    constructor(): this(null, "", "", "", "", Gender.Male, "", null)
}