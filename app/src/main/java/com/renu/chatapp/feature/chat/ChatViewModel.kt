package com.renu.chatapp.feature.chat

import androidx.core.net.toUri
import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.remote.ChannelRepo
import com.renu.chatapp.data.remote.StorageRepo
import com.renu.chatapp.domain.model.Channel
import com.renu.chatapp.domain.model.Message
import com.renu.chatapp.domain.model.User
import com.renu.chatapp.domain.model.ext.id
import com.renu.chatapp.feature.chat.ChatViewModel.ChatListItem.ReceivedMessage
import com.renu.chatapp.feature.chat.ChatViewModel.ChatListItem.SentMessages
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.taskState.taskStateOf
import com.streamliners.base.taskState.update
import com.streamliners.base.taskState.value
import com.streamliners.utils.DateTimeUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatViewModel(
    private val channelRepo: ChannelRepo,
    private val localRepo: LocalRepo,
    private val storageRepo: StorageRepo
) : BaseViewModel() {

    sealed class ChatListItem{
        class ReceivedMessage(
            val time: String,
            val message: Message
        ): ChatListItem()

        class Date(val date: String) : ChatListItem()

        class SentMessages(
            val time: String,
            val message: Message
        ) : ChatListItem()
    }

    class Data(
        val channel: Channel,
        val user: User,
        val chatListItems: List<ChatListItem>
    )

    val data = taskStateOf<Data>()

    fun start(
        channelId: String
    ) {
        execute {
            val user = localRepo.getLoggedInUser()
            launch {
                channelRepo.subscribeToChannel(channelId).collectLatest {
                    val channel = channelRepo.getChannel(channelId)
                    data.update(
                        Data(channel, user, createChatListItems(channel, user.id()))
                    )
                }
            }
        }
    }

    private fun createChatListItems (channel: Channel, currentUserId: String): List<ChatListItem>{

        return buildList {
            var prevDateString =""
            channel.messages.forEach {message ->

                val dateString = DateTimeUtils.formatTime(
                    DateTimeUtils.Format.DATE_MONTH_YEAR_1,
                    message.time.toDate().time
                )

                if (prevDateString != dateString){
                    add(ChatListItem.Date(dateString))
                    prevDateString = dateString
                }

                val chatListItem = if (message.sender == currentUserId){
                    SentMessages(
                        DateTimeUtils.formatTime(
                            DateTimeUtils.Format.HOUR_MIN_12, message.time.toDate().time
                        ),
                        message
                    )
                } else {
                    ReceivedMessage(
                        DateTimeUtils.formatTime(
                            DateTimeUtils.Format.HOUR_MIN_12, message.time.toDate().time
                        ),
                        message
                    )
                }
                add(chatListItem)
            }
        }
    }

    fun sendMessage(
        messageStr: String,
        onSuccess: () -> Unit
        ) {
        execute{
            val message = Message(
                sender =data.value().user.id(),
                message = messageStr,
                mediaUrl = null
            )
            channelRepo.sendMessage(data.value().channel.id(), message)
            onSuccess()
        }
    }

    fun sendImage(uri: String) {
        execute{
            val email = localRepo.getLoggedInUser().email
            val timestamp = System.currentTimeMillis()

            // TODO : Use the ext file extension
            val imageUrl = storageRepo.uploadFile("media/$timestamp-$email.jpg", uri.toUri())

            val message = Message(
                sender = data.value().user.id(),
                message = "",
                mediaUrl = imageUrl
            )
            channelRepo.sendMessage(data.value().channel.id(), message)

        }

    }
}