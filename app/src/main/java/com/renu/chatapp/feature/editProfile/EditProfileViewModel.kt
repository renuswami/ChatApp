package com.renu.chatapp.feature.editProfile

import androidx.core.net.toUri
import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.data.remote.StorageRepo
import com.renu.chatapp.domain.model.User
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.ext.executeOnMain
import com.streamliners.base.taskState.load
import com.streamliners.base.taskState.taskStateOf
import com.streamliners.pickers.media.PickedMedia
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val localRepo: LocalRepo,
    private val storageRepo: StorageRepo
) : BaseViewModel() {

    val saveProfileTask = taskStateOf<Unit>()
    fun saveUser(
        user: User,
        image: PickedMedia?,
        onSuccess: () -> Unit,
    ) {
        execute(showLoadingDialog = false) {
            saveProfileTask.load {
                var updatedUser = user.copy(
                    profileImageUrl = uplaodProfileImage(user.email, image)
                )

                updatedUser = userRepo.saveUser(user = updatedUser)
                localRepo.onLoggedIn(updatedUser)
                executeOnMain { onSuccess() }
            }
        }

    }

    private suspend fun uplaodProfileImage(email: String, image: PickedMedia?): String? {
        return image?.let {
            // TODO : Save image using userId
            // TODO : Use the exact file extension
            storageRepo.uploadFile("profileImages/$email.jpg", it.uri.toUri())
        }
    }
}