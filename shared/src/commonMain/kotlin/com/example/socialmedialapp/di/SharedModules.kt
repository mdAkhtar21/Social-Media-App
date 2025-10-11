package com.example.socialmedialapp.di

import com.example.socialmedialapp.account.data.AccountApiService
import com.example.socialmedialapp.account.data.repository.ProfileRepositoryImpl
import com.example.socialmedialapp.account.domain.repository.ProfileRepository
import com.example.socialmedialapp.account.domain.usecase.GetProfileUseCase
import com.example.socialmedialapp.auth.data.AuthRepositoryImpl
import com.example.socialmedialapp.auth.data.AuthService
import com.example.socialmedialapp.auth.domain.repository.AuthRepository
import com.example.socialmedialapp.auth.domain.usecase.SignInUsecase
import com.example.socialmedialapp.auth.domain.usecase.SignUpUseCase
import com.example.socialmedialapp.common.data.remote.FollowsApiService
import com.example.socialmedialapp.common.data.remote.PostApiService
import com.example.socialmedialapp.common.util.provideDispatcher
import com.example.socialmedialapp.follows.data.FollowsRepositoryImpl
import com.example.socialmedialapp.follows.domain.FollowsRepository
import com.example.socialmedialapp.follows.domain.usecase.FollowOrUnfollowUseCase
import com.example.socialmedialapp.follows.domain.usecase.GetFollowableUsersUseCase
import com.example.socialmedialapp.post.data.PostRepositoryImpl
import com.example.socialmedialapp.post.domain.repository.PostRepository
import com.example.socialmedialapp.post.domain.usecase.GetPostsUseCase
import com.example.socialmedialapp.post.domain.usecase.GetUserPostsUseCase
import com.example.socialmedialapp.post.domain.usecase.LikeOrDislikePostUseCase
import org.koin.dsl.module

private val authModule= module {
    single<AuthRepository> { AuthRepositoryImpl(get(),get(),get()) }
    factory { AuthService() }
    factory { SignInUsecase() }
    factory { SignUpUseCase()}
}
private val postModule = module {
    factory { PostApiService() }
    factory { GetPostsUseCase() }
    factory { LikeOrDislikePostUseCase() }
    factory { GetUserPostsUseCase() }
    single<PostRepository> { PostRepositoryImpl(get(), get(), get()) }
}
private val followsModule = module {
    factory { FollowsApiService() }
    factory { GetFollowableUsersUseCase() }
    factory { FollowOrUnfollowUseCase() }

    single<FollowsRepository> { FollowsRepositoryImpl(get(), get(), get()) }
}
private val  accountModule = module {
    factory { AccountApiService() }
    factory { GetProfileUseCase() }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get()) }
}


private val utilityModule= module {
    factory { provideDispatcher() }
}


fun getSharedModules()= listOf(platformModule,authModule, utilityModule, accountModule)