package com.renu.chatapp.domain.model.ext

import com.renu.chatapp.domain.model.User

fun User.id(): String{
    return id ?: error("id not found")
}