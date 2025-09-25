package com.example.socialmedialapp.android.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.socialmedialapp.android.MainActivityViewModel
import com.example.socialmedialapp.android.auth.Login.LoginViewModel
import com.example.socialmedialapp.android.auth.signUp.SignUpViewModel
import com.example.socialmedialapp.android.common.datastore.UserSettingsSerializer
import com.example.socialmedialapp.android.edit.EditProfileViewModel
import com.example.socialmedialapp.android.follows.FollowsViewModel
import com.example.socialmedialapp.android.home.HomeScreenViewModel
import com.example.socialmedialapp.android.post.PostDetailScreen
import com.example.socialmedialapp.android.post.PostDetailScreenViewModel
import com.example.socialmedialapp.android.profile.ProfileScreen
import com.example.socialmedialapp.android.profile.ProfileViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule= module {
    viewModel{LoginViewModel(get(),get())}
    viewModel{SignUpViewModel(get(),get())}
    viewModel{MainActivityViewModel(get())}
    viewModel{HomeScreenViewModel()}
    viewModel{ PostDetailScreenViewModel() }
    viewModel{ ProfileViewModel()}
    viewModel{EditProfileViewModel()}
    viewModel{FollowsViewModel()}

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