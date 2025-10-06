package com.example.socialmedialapp.follows.data

import androidx.datastore.core.IOException
import com.example.socialmedialapp.common.data.local.UserPreferences
import com.example.socialmedialapp.common.data.model.FollowsParams
import com.example.socialmedialapp.common.data.remote.FollowsApiService
import com.example.socialmedialapp.common.domain.model.FollowsUser
import com.example.socialmedialapp.common.util.Constants
import com.example.socialmedialapp.common.util.DispatcherProvider
import com.example.socialmedialapp.follows.domain.FollowsRepository
import com.example.socialmedialapp.common.util.Result
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext

internal class FollowsRepositoryImpl(
    private val followsApiService: FollowsApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
): FollowsRepository {
    override suspend fun getFollowableUsers():Result<List<FollowsUser>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = followsApiService.getFollowableUsers(userData.token, userData.id)

                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.follows.map { it.toDomainFollowUser() })
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    HttpStatusCode.Forbidden -> {
                        Result.Success(data = emptyList())
                    }

                    else -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_ERROR)
            }
        }
    }

    override suspend fun followOrUnfollow(
        FollowsUserId: Long,
        shouldFollow: Boolean
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val followParams = FollowsParams(userData.id, FollowsUserId)

                val apiResponse = if (shouldFollow) {
                    followsApiService.followUser(userData.token, followParams)
                } else {
                    followsApiService.unfollowUser(userData.token, followParams)
                }

                if (apiResponse.code == HttpStatusCode.OK) {
                    Result.Success(data = apiResponse.data.success)
                } else {
                    Result.Error(data = false, message = "${apiResponse.data.message}")
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_ERROR)
            } catch (exception: Throwable) {
                Result.Error(
                    message = "${exception.message}"
                )
            }
        }
    }
}