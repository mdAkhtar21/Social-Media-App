package com.example.socialmedialapp.post.domain.usecase

import com.example.socialmedialapp.common.util.Result
import com.example.socialmedialapp.post.domain.model.PostComment
import com.example.socialmedialapp.post.domain.repository.PostCommentsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject



class RemovePostCommentUseCase: KoinComponent {
    private val repository: PostCommentsRepository by inject()

    suspend operator fun invoke(postId: Long, commentId: Long): Result<PostComment?> {
        return repository.removeComment(postId = postId, commentId = commentId)
    }
}