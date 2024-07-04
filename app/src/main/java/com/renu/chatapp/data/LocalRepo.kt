package com.renu.chatapp.data

import com.renu.chatapp.domain.model.User
import com.renu.chatapp.helper.DataStoreUtil

class LocalRepo (
    private val dataStoreUtil: DataStoreUtil
    ){

    companion object{
        private const val KEY_USER = "user"
        private const val KEY_IS_SUBSCRIBED_FOR_GROUP_NOTIFICATIONS = "IS_SUBSCRIBED_FOR_GROUP_NOTIFICATIONS "
    }

    suspend fun upsertCurrentUser(user: User){
        dataStoreUtil.setData(KEY_USER, user)
    }

    suspend fun getLoggedInUser(): User {
        return getLoggedInUserNullble() ?: error("User not found in ;local")
    }

    private suspend fun getLoggedInUserNullble(): User? {
        return dataStoreUtil.getData<User>(KEY_USER)
    }

    suspend fun isLoggedIn() = getLoggedInUserNullble() != null

    suspend fun isSubscribedForGroupNotifications(): Boolean{
        return dataStoreUtil.getData(KEY_IS_SUBSCRIBED_FOR_GROUP_NOTIFICATIONS) ?: false
    }

    suspend fun onSubscribedForGroupNotifications(){
        dataStoreUtil.setData(KEY_IS_SUBSCRIBED_FOR_GROUP_NOTIFICATIONS, true)
    }
}