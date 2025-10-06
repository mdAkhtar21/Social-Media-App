package com.example.socialmedialapp.common.data.remote
import com.example.socialmedialapp.common.data.model.FollowOrUnfollowApiResponse
import com.example.socialmedialapp.common.data.model.FollowsApiResponse
import com.example.socialmedialapp.common.data.model.FollowsParams
import com.example.socialmedialapp.common.util.Constants
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class FollowsApiService : KtorApi() {

    suspend fun followUser(
        userToken: String,
        followsParams: FollowsParams
    ): FollowOrUnfollowApiResponse {
        val httpResponse = client.post {
            endPoint(path = "/follows/follow")
            setBody(followsParams)
            setToken(userToken)
        }

        return FollowOrUnfollowApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun unfollowUser(
        userToken: String,
        followsParams: FollowsParams
    ): FollowOrUnfollowApiResponse {
        val httpResponse = client.post {
            endPoint(path = "/follows/unfollow")
            setBody(followsParams)
            setToken(userToken)
        }
        return FollowOrUnfollowApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun getFollowableUsers(userToken: String, userId: Int): FollowsApiResponse {
        val httpResponse = client.get {
            endPoint(path = "/follows/suggestions")
            parameter(key = Constants.USER_ID_PARAMETER, value = userId)
            setToken(userToken)
        }
        return FollowsApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

}