package com.example.socialmedialapp.android.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.socialmedialapp.android.MainActivityViewModel
import com.example.socialmedialapp.android.auth.Login.LoginViewModel
import com.example.socialmedialapp.android.auth.signUp.SignUpViewModel
import com.example.socialmedialapp.common.data.UserSettingsSerializer
import com.example.socialmedialapp.android.account.edit.EditProfileViewModel
import com.example.socialmedialapp.android.account.follows.FollowsViewModel
import com.example.socialmedialapp.android.home.HomeScreenViewModel
import com.example.socialmedialapp.android.post.PostDetailScreenViewModel
import com.example.socialmedialapp.android.account.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule= module {
    viewModel{LoginViewModel(get(),get())}
    viewModel{SignUpViewModel(get(),get())}
    viewModel{MainActivityViewModel(get())}
    viewModel{HomeScreenViewModel(get(),get(),get(),get())}
    viewModel{ PostDetailScreenViewModel() }
    viewModel{ ProfileViewModel() }
    viewModel{ EditProfileViewModel() }
    viewModel{ FollowsViewModel() }
}