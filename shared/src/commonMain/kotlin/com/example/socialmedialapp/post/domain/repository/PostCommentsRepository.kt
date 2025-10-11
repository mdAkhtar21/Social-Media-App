package com.example.socialmedialapp.post.domain.repository

import com.example.socialmedialapp.post.domain.model.PostComment
import com.example.socialmedialapp.common.util.Result


internal interface PostCommentsRepository {
    suspend fun getPostComments(postId: Long, page: Int, pageSize: Int): Result<List<PostComment>>
    suspend fun addComment(postId: Long, content: String): Result<PostComment>
    suspend fun removeComment(postId: Long, commentId: Long): Result<PostComment?>
}