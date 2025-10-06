package com.example.socialmedialapp.follows.domain.usecase

import com.example.socialmedialapp.common.util.Result
import com.example.socialmedialapp.follows.domain.FollowsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FollowOrUnfollowUseCase :KoinComponent{
    private val repository by inject<FollowsRepository>()

    suspend operator fun invoke(
        followedUserId: Long,
        shouldFollow: Boolean
    ): Result<Boolean> {
        return repository.followOrUnfollow(followedUserId, shouldFollow)
    }
}