package com.renu.chatapp.feature.editProfile

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val localRepo: LocalRepo,
    private val storageRepo: StorageRepo
) : BaseViewModel() {

    val saveProfileTask = taskStateOf<Unit>()

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()

    fun saveUser(
        user: User,
        image: PickedMedia?,
        onSuccess: () -> Unit,
    ) {
        execute(showLoadingDialog = false) {
            saveProfileTask.load {
                val token = Firebase.messaging.token.await()

                var updatedUser = user.copy(
                    profileImageUrl = uplaodProfileImage(user.email, image),
                    fcmToken = token
                )

                updatedUser = userRepo.saveUser(user = updatedUser)
                localRepo.upsertCurrentUser(updatedUser)
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


    fun loadUser() {
        viewModelScope.launch {
            val user = localRepo.getLoggedInUser()
            _userState.value = user
            Log.d("UserData", user.toString())
        }
    }

}