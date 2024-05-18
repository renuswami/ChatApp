package com.renu.chatapp.feature.editProfile

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.data.remote.StorageRepo
import com.renu.chatapp.domain.model.User
import com.streamliners.pickers.media.PickedMedia
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val userRepo:UserRepo,
    private val localRepo: LocalRepo,
    private val storageRepo: StorageRepo
): ViewModel(){

    fun saveUser(
        user: User,
        image: PickedMedia?,
        onSuccess: () -> Unit
    ){
        viewModelScope.launch {

            val updatedUser = user.copy(
            profileImageUrl = uplaodProfileImage(user.email, image)
            )

            userRepo.saveUser(
                user = updatedUser
            )
           localRepo.onLoggedIn()

            onSuccess()
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