package com.renu.chatapp.feature.home

import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.remote.ChannelRepo
import com.renu.chatapp.domain.model.Channel
import com.renu.chatapp.domain.model.ext.id
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.taskState.load
import com.streamliners.base.taskState.taskStateOf

class HomeViewModel(
    private val localRepo: LocalRepo,
    private val channelRepo: ChannelRepo
) : BaseViewModel() {
    val channels = taskStateOf<List<Channel>>()
    fun start() {
        execute {
            val userId = localRepo.getLoggedInUser().id()
            channels.load {
                channelRepo.getAllChannelsOf(userId)
            }
        }
    }
}