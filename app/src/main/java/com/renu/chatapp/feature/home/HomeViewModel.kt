package com.renu.chatapp.feature.home

import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.data.remote.ChannelRepo
import com.renu.chatapp.domain.model.Channel
import com.renu.chatapp.domain.model.ext.id
import com.renu.chatapp.domain.model.ext.profileImageUrl
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.taskState.load
import com.streamliners.base.taskState.taskStateOf
import com.streamliners.base.taskState.update

class HomeViewModel(
    private val localRepo: LocalRepo,
    private val channelRepo: ChannelRepo,
    private val userRepo: UserRepo
) : BaseViewModel() {
    val channelsState = taskStateOf<List<Channel>>()
    fun start() {
        execute {
            val userId = localRepo.getLoggedInUser().id()

            val users = userRepo.getAllUsers()
            val channels = channelRepo.getAllChannelsOf(userId)
                .map {channel ->
                    if (channel.type == Channel.Type.OneToOne){
                        val otherUserId = channel.members.find { it != userId }
                            ?: error("otherUserId not found")
                        val otherUser = users.find { it.id() == otherUserId }
                            ?: error("user with id $otherUserId not found")

                        channel.copy(
                            name = otherUser.name,
                            imageUrl = otherUser.profileImageUrl()
                        )
                    } else {
                        channel
                    }
            }
            channelsState.update(channels)

        }
    }
}