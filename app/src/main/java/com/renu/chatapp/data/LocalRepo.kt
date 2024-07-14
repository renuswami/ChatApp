package com.renu.chatapp.data

import com.renu.chatapp.domain.model.User
import com.renu.chatapp.helper.DataStoreUtil

class LocalRepo (
    private val dataStoreUtil: DataStoreUtil
    ){

    companion object{
        private const val KEY_USER = "user"
    }

    suspend fun upsertCurrentUser(user: User){
        dataStoreUtil.setData(KEY_USER, user)
    }

    suspend fun getLoggedInUser(): User {
        return getLoggedInUserNullble() ?: error("User not found in ;local")
    }

    suspend fun getLoggedInUserNullble(): User? {
        return dataStoreUtil.getData<User>(KEY_USER)
    }

    suspend fun isLoggedIn() = getLoggedInUserNullble() != null

}