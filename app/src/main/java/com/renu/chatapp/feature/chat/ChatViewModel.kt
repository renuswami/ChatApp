package com.renu.chatapp.feature.chat

import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.remote.ChannelRepo
import com.renu.chatapp.domain.model.Channel
import com.renu.chatapp.domain.model.Message
import com.renu.chatapp.domain.model.User
import com.renu.chatapp.domain.model.ext.id
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.taskState.taskStateOf
import com.streamliners.base.taskState.update
import com.streamliners.base.taskState.value
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatViewModel(
    private val channelRepo: ChannelRepo,
    private val localRepo: LocalRepo
) : BaseViewModel() {

    val channel = taskStateOf<Channel>()
    lateinit var user: User
    fun start(
        channelId: String
    ) {
        execute {
            user = localRepo.getLoggedInUser()
            launch {
                channelRepo.subscribeToChannel(channelId).collectLatest {
                    channel.update(channelRepo.getChannel(channelId))
                }
            }
        }
    }

    fun sendMessage(
        messageStr: String,
        onSuccess: () -> Unit
        ) {
        execute{
            val message = Message(
                sender =user.id(),
                message = messageStr,
                mediaUrl = null
            )
            channelRepo.sendMessage(channel.value().id(), message)
            onSuccess()
        }

    }
}