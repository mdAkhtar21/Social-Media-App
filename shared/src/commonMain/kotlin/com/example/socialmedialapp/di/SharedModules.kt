package com.example.socialmedialapp.di

import com.example.socialmedialapp.auth.data.AuthRepositoryImpl
import com.example.socialmedialapp.auth.data.AuthService
import com.example.socialmedialapp.auth.domain.repository.AuthRepository
import com.example.socialmedialapp.auth.domain.usecase.SignInUsecase
import com.example.socialmedialapp.auth.domain.usecase.SignUpUseCase
import com.example.socialmedialapp.common.util.provideDispatcher
import org.koin.dsl.module

private val authModule= module {
    single<AuthRepository> { AuthRepositoryImpl(get(),get()) }
    factory { AuthService() }
    factory { SignInUsecase() }
    factory { SignUpUseCase()}
}

private val utilityModule= module {
    factory { provideDispatcher() }
}


fun getSharedModules()= listOf(authModule, utilityModule)