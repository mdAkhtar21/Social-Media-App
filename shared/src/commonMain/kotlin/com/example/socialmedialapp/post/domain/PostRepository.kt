package com.example.socialmedialapp.post.domain

import com.example.socialmedialapp.common.domain.model.Post
import com.example.socialmedialapp.common.util.Result

interface PostRepository {
    suspend fun getFeedPosts(page: Int, pageSize: Int): Result<List<Post>>
    suspend fun likeOrDislikePost(postId: Long, shouldLike: Boolean): Result<Boolean>
}