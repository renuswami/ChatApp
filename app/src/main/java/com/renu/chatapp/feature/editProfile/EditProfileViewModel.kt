package com.renu.chatapp.feature.editProfile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.data.remote.StorageRepo
import com.renu.chatapp.domain.model.User
import com.renu.chatapp.ui.comp.ImageState
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.ext.executeOnMain
import com.streamliners.base.taskState.load
import com.streamliners.base.taskState.taskStateOf
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
    var currentUser: User? = null
    var email = mutableStateOf("")

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()

    fun saveUser(
        user: User,
        image: ImageState,
        onSuccess: () -> Unit,
    ) {
        execute(showLoadingDialog = false) {
            saveProfileTask.load {
                val token = Firebase.messaging.token.await()

                var updatedUser = user.copy(
                    profileImageUrl = uplaodProfileImage(user.email, image),
                    fcmToken = token,
                    id = currentUser?.id
                )

                updatedUser = userRepo.upsertUser(user = updatedUser)
                localRepo.upsertCurrentUser(updatedUser)
                executeOnMain { onSuccess() }
            }
        }
    }

     fun getCurrentUser(
         onSuccess: (User) -> Unit,
         onNotFount: () -> Unit
     ) {
         execute(false) {
             localRepo.getLoggedInUserNullble()?.let { user ->
                 currentUser = user
                 onSuccess(user)
             } ?: run { onNotFount }
         }
     }

    private suspend fun uplaodProfileImage(email: String, imageState: ImageState): String? {
        return when (imageState) {
            ImageState.Empty -> null
            is ImageState.Exists -> imageState.url
            is ImageState.New -> {
                storageRepo.uploadFile(
                    path = "profileImages/$email.jpg",
                    uri = imageState.pickedMedia.uri.toUri()
                )
            }
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