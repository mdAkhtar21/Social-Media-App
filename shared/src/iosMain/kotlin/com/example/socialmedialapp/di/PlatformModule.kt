package com.example.socialmedialapp.di

import com.example.socialmedialapp.common.data.IOSUserPreferences
import com.example.socialmedialapp.common.data.createDatastore
import com.example.socialmedialapp.common.data.local.UserPreferences
import org.koin.dsl.module


actual val platformModule = module {
    single<UserPreferences> { IOSUserPreferences(get()) }

    single {
        createDatastore()
    }
}