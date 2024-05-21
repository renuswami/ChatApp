package com.renu.chatapp.feature.newChat

import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.data.remote.ChannelRepo
import com.renu.chatapp.domain.model.User
import com.renu.chatapp.domain.model.ext.id
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.ext.executeOnMain
import com.streamliners.base.taskState.load
import com.streamliners.base.taskState.taskStateOf

class NewChatViewModel(
    private val userRepo: UserRepo,
    private val localRepo: LocalRepo,
    private val channelRepo: ChannelRepo

): BaseViewModel() {

    val usersListTask = taskStateOf<List<User>>()
    fun start(){
        execute{
            val user = localRepo.getLoggedInUser()
            usersListTask.load {
                userRepo.getAllUsers().filter {  it.id != user.id() }
            }
        }
    }

    fun onUserSelected(
        otherUserId: String,
        onChannelReady: (String) -> Unit
        ){
        execute {
            val currentUserId = localRepo.getLoggedInUser().id()
            val channel = channelRepo.getOneToOneChannel(currentUserId, otherUserId)
            val channelId = if(channel != null){
                channel.id()
            }else{
                channelRepo.createOneToOneChannel(currentUserId, otherUserId)
            }
            executeOnMain {
                onChannelReady(channelId)
            }
        }
    }
}