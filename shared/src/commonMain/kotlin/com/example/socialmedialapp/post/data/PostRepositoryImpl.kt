package com.example.socialmedialapp.post.data

import androidx.datastore.core.IOException
import com.example.socialmedialapp.common.data.local.UserPreferences
import com.example.socialmedialapp.common.data.model.LikeParams
import com.example.socialmedialapp.common.data.remote.PostApiService
import com.example.socialmedialapp.common.domain.model.Post
import com.example.socialmedialapp.common.util.Constants
import com.example.socialmedialapp.common.util.DispatcherProvider
import com.example.socialmedialapp.common.util.Result
import com.example.socialmedialapp.post.domain.PostRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext

internal class PostRepositoryImpl(
    private val postApiService: PostApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : PostRepository {
    override suspend fun getFeedPosts(page: Int, pageSize: Int): Result<List<Post>> {
        return withContext(dispatcher.io){
            try {
                val userData=userPreferences.getUserData()
                val apiResponse=postApiService.getFeedPosts(
                    userToken = userData.token,
                    currentUserId = userData.id,
                    page =page,
                    pageSize =pageSize
                )
                when(apiResponse.code){
                    HttpStatusCode.OK->{
                        Result.Success(data = apiResponse.data.posts.map { it.toDomainPost() })
                    }
                    HttpStatusCode.BadRequest->{
                        Result.Error(message = "Error :${apiResponse.data.message}")
                    }
                    else ->{
                        Result.Error(message = Constants.UNEXPECTED_ERROR)
                    }
                }
            }catch (ioException:IOException){
                Result.Error(message = Constants.NO_INTERNET_ERROR)
            }catch (exception:Throwable){
                Result.Error(
                    message = "${exception.cause}"
                )
            }
        }
    }

    override suspend fun likeOrDislikePost(postId: Long, shouldLike: Boolean): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val likeParams = LikeParams(postId = postId, userId = userData.id)

                val apiResponse = if (shouldLike){
                    postApiService.likePost(userData.token, likeParams)
                }else{
                    postApiService.dislikePost(userData.token, likeParams)
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