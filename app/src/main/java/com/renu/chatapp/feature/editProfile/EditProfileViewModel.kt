package com.renu.chatapp.feature.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.domain.model.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val userRepo:UserRepo,
    private val localRepo: LocalRepo
): ViewModel(){

    fun saveUser(user: User, onSuccess: () -> Unit){
        viewModelScope.launch {
            userRepo.saveUser(user = user)
           localRepo.onLoggedIn()

            onSuccess()
        }
    }
}