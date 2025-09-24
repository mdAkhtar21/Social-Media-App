package com.example.socialmedialapp.android.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.socialmedialapp.android.MainActivityViewModel
import com.example.socialmedialapp.android.auth.Login.LoginViewModel
import com.example.socialmedialapp.android.auth.signUp.SignUpViewModel
import com.example.socialmedialapp.android.common.datastore.UserSettingsSerializer
import com.example.socialmedialapp.android.home.HomeScreenViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule= module {
    viewModel{LoginViewModel(get(),get())}
    viewModel{SignUpViewModel(get(),get())}
    viewModel{MainActivityViewModel(get())}
    viewModel{HomeScreenViewModel()}

    single{
        DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = {
                androidContext().dataStoreFile(
                    fileName = "app_user_settings"
                )
            }
        )
    }
}