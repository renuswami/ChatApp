package com.renu.chatapp.domain.model.ext

import com.renu.chatapp.domain.model.User
import com.renu.chatapp.helper.userInitialBasedProfileImage

fun User.id(): String{
    return id ?: error("id not found")
}

fun User.profileImageUrl(): String{
    return profileImageUrl ?: userInitialBasedProfileImage(name)
}