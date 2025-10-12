package com.example.socialmedialapp.di

import com.example.socialmedialapp.account.data.AccountApiService
import com.example.socialmedialapp.account.data.repository.ProfileRepositoryImpl
import com.example.socialmedialapp.account.domain.repository.ProfileRepository
import com.example.socialmedialapp.account.domain.usecase.GetProfileUseCase
import com.example.socialmedialapp.account.domain.usecase.UpdateProfileUseCase
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
import com.example.socialmedialapp.follows.domain.usecase.GetFollowsUseCase
import com.example.socialmedialapp.post.data.PostRepositoryImpl
import com.example.socialmedialapp.post.data.remote.PostCommentsApiService
import com.example.socialmedialapp.post.data.repository.PostCommentsRepositoryImpl
import com.example.socialmedialapp.post.domain.repository.PostCommentsRepository
import com.example.socialmedialapp.post.domain.repository.PostRepository
import com.example.socialmedialapp.post.domain.usecase.AddPostCommentUseCase
import com.example.socialmedialapp.post.domain.usecase.CreatePostUseCase
import com.example.socialmedialapp.post.domain.usecase.GetPostCommentsUseCase
import com.example.socialmedialapp.post.domain.usecase.GetPostUseCase
import com.example.socialmedialapp.post.domain.usecase.GetPostsUseCase
import com.example.socialmedialapp.post.domain.usecase.GetUserPostsUseCase
import com.example.socialmedialapp.post.domain.usecase.LikeOrDislikePostUseCase
import com.example.socialmedialapp.post.domain.usecase.RemovePostCommentUseCase
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
    factory { GetPostUseCase() }
    factory { CreatePostUseCase() }
    single<PostRepository> { PostRepositoryImpl(get(), get(), get()) }
}
private val postCommentModule = module {
    factory { PostCommentsApiService() }
    factory { GetPostCommentsUseCase() }
    factory { AddPostCommentUseCase() }
    factory { RemovePostCommentUseCase() }

    single<PostCommentsRepository> { PostCommentsRepositoryImpl(get(), get(), get()) }
}
private val followsModule = module {
    factory { FollowsApiService() }
    factory { GetFollowableUsersUseCase() }
    factory { FollowOrUnfollowUseCase() }
    factory { GetFollowsUseCase() }

    single<FollowsRepository> { FollowsRepositoryImpl(get(), get(), get()) }
}
private val  accountModule = module {
    factory { AccountApiService() }
    factory { GetProfileUseCase() }
    factory { UpdateProfileUseCase() }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get()) }
}


private val utilityModule= module {
    factory { provideDispatcher() }
}


fun getSharedModules()= listOf(platformModule,authModule,followsModule,postModule, utilityModule, accountModule, postCommentModule)