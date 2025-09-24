package com.example.socialmedialapp.android

import android.app.Application
import com.example.socialmedialapp.android.di.appModule
import com.example.socialmedialapp.di.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SocialApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(appModule+ getSharedModules())
            androidContext(this@SocialApplication)
        }
    }
}