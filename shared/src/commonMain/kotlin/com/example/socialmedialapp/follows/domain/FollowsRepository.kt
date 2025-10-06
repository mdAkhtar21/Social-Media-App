package com.example.socialmedialapp.follows.domain

import com.example.socialmedialapp.common.domain.model.FollowsUser
import com.example.socialmedialapp.common.util.Result

interface FollowsRepository {

    suspend fun getFollowableUsers(): Result<List<FollowsUser>>
    suspend fun followOrUnfollow(FollowsUserId:Long,shouldFollow:Boolean):Result<Boolean>
}