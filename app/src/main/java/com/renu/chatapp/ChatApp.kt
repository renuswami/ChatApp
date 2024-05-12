package com.renu.chatapp

import android.app.Application
import com.renu.chatapp.helper.appModule
import com.renu.chatapp.helper.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChatApp : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ChatApp)
            modules(appModule, viewModelModule)
        }
    }
}