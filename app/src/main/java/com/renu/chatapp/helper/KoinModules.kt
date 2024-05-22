package com.renu.chatapp.helper

import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.data.remote.ChannelRepo
import com.renu.chatapp.data.remote.StorageRepo
import com.renu.chatapp.feature.chat.ChatViewModel
import com.renu.chatapp.feature.editProfile.EditProfileViewModel
import com.renu.chatapp.feature.home.HomeViewModel
import com.renu.chatapp.feature.login.LoginViewModel
import com.renu.chatapp.feature.newChat.NewChatViewModel
import com.renu.chatapp.feature.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { UserRepo() }
    single { LocalRepo(DataStoreUtil.create(get())) }
    single { StorageRepo() }
    single { ChannelRepo() }
}

val viewModelModule = module {
    viewModel { EditProfileViewModel(get(), get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { NewChatViewModel (get(), get(), get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { ChatViewModel(get(), get()) }
}