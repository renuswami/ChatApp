package com.renu.chatapp.feature.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.domain.model.User
import kotlinx.coroutines.launch

class EditProfileViewModel: ViewModel(){
    fun saveUser(user: User, onSuccess: () -> Unit){
        viewModelScope.launch {
            UserRepo().saveUser(user = user)
            onSuccess()
        }
    }
}