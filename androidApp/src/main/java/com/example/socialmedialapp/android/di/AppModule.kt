package com.example.socialmedialapp.android.di

import com.example.socialmedialapp.android.MainActivityViewModel
import com.example.socialmedialapp.android.account.edit.EditProfileViewModel
import com.example.socialmedialapp.android.account.follows.FollowsViewModel
import com.example.socialmedialapp.android.account.profile.ProfileViewModel
import com.example.socialmedialapp.android.auth.Login.LoginViewModel
import com.example.socialmedialapp.android.auth.signUp.SignUpViewModel
import com.example.socialmedialapp.android.common.util.ImageBytesReader
import com.example.socialmedialapp.android.home.HomeScreenViewModel
import com.example.socialmedialapp.android.post.PostDetailViewModel
import com.example.socialmedialapp.android.post.create_post.CreatePostViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule= module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { MainActivityViewModel(get())}
    viewModel { HomeScreenViewModel(get(), get(), get(), get())}
    viewModel { PostDetailViewModel(get(), get(), get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get(), get()) }
    viewModel { EditProfileViewModel(get(), get(), get()) }
    viewModel { FollowsViewModel(get()) }
    single { ImageBytesReader(androidContext()) }
    viewModel { CreatePostViewModel(get(), get()) }
}