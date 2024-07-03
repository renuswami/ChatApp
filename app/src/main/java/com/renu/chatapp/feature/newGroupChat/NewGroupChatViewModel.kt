package com.renu.chatapp.feature.newGroupChat

import androidx.core.net.toUri
import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.data.remote.ChannelRepo
import com.renu.chatapp.data.remote.StorageRepo
import com.renu.chatapp.domain.model.User
import com.renu.chatapp.domain.model.ext.id
import com.streamliners.base.BaseViewModel
import com.streamliners.base.exception.failure
import com.streamliners.base.ext.execute
import com.streamliners.base.ext.executeOnMain
import com.streamliners.base.taskState.load
import com.streamliners.base.taskState.taskStateOf
import com.streamliners.pickers.media.PickedMedia

class NewGroupChatViewModel(
    private val userRepo: UserRepo,
    private val localRepo: LocalRepo,
    private val channelRepo: ChannelRepo,
    private val storageRepo: StorageRepo

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

    fun createGroupChannel(
        name: String,
        description: String?,
        groupImage: PickedMedia?,
        members: List<String>,
        onChannelReady: (String) -> Unit
        ){
        execute {

            if(members.size < 2) failure("Members must be >= 2")

            val currentUserId = localRepo.getLoggedInUser().id()

            val groupImageUrl = uplaodProfileImage(name, groupImage)
            val channelId = channelRepo.createGroupChannel(
                currentUserId, name, description, groupImageUrl, members
            )

            executeOnMain { onChannelReady(channelId) }
        }
    }
    private suspend fun uplaodProfileImage(name: String, image: PickedMedia?): String? {
        return image?.let {
            // TODO : Save image using userId
            // TODO : Use the exact file extension
            storageRepo.uploadFile("groupImages/$name-${System.currentTimeMillis()}.jpg", it.uri.toUri())
        }
    }
}