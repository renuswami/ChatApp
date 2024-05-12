package com.renu.chatapp.helper

import com.renu.chatapp.data.LocalRepo
import com.renu.chatapp.data.UserRepo
import com.renu.chatapp.feature.editProfile.EditProfileViewModel
import com.renu.chatapp.feature.login.LoginScreen
import com.renu.chatapp.feature.login.LoginViewModel
import com.renu.chatapp.feature.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val appModule = module {
    single { UserRepo()}
    single {LocalRepo(DataStoreUtil.create(get()))}
}

val viewModelModule = module {
    viewModel { EditProfileViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get())}
}