package com.renu.chatapp.feature.newChat

import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.domain.model.User
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.taskState.load
import com.streamliners.base.taskState.taskStateOf

class NewChatViewModel(
    private val userRepo: UserRepo,
    private val localRepo: LocalRepo
): BaseViewModel() {

    val usersListTask = taskStateOf<List<User>>()
    fun start(){
        execute{
            val user = localRepo.getLoggedInUser()
            usersListTask.load {
                userRepo.getAllUsers().filter {  it.id != user.id }
            }
        }
    }
}