package com.renu.chatapp.helper

fun userInitialBasedProfileImage(name: String): String{
    return "https://placehold.co/400x400/8679C6/FFFFFF/png?text=${name.first()}"
}