package com.renu.chatapp.domain.model.ext

import com.renu.chatapp.domain.model.Channel

fun Channel.id(): String {
    return id ?: error("id not found")
}