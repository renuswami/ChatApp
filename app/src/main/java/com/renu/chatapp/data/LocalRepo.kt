package com.renu.chatapp.data

import com.renu.chatapp.domain.model.User
import com.renu.chatapp.helper.DataStoreUtil

class LocalRepo (
    private val dataStoreUtil: DataStoreUtil
    ){
    suspend fun upsertCurrentUser(user: User){
        dataStoreUtil.setData("user", user)
    }

    suspend fun getLoggedInUser(): User {
        return getLoggedInUserNullble() ?: error("User not found in ;local")
    }

    private suspend fun getLoggedInUserNullble(): User? {
        return dataStoreUtil.getData<User>("user")
    }

    suspend fun isLoggedIn() = getLoggedInUserNullble() != null
}