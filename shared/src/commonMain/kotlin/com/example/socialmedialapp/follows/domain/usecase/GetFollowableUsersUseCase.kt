package com.example.socialmedialapp.follows.domain.usecase

import com.example.socialmedialapp.common.domain.model.FollowsUser
import com.example.socialmedialapp.common.util.Result
import com.example.socialmedialapp.follows.domain.FollowsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetFollowableUsersUseCase:KoinComponent {
    private val repository by inject<FollowsRepository>()

    suspend operator fun invoke(): Result<List<FollowsUser>> {
        return repository.getFollowableUsers()
    }
}