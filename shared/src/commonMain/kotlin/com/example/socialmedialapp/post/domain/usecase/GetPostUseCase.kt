package com.example.socialmedialapp.post.domain.usecase

import com.example.socialmedialapp.common.domain.model.Post
import com.example.socialmedialapp.common.util.Result
import com.example.socialmedialapp.post.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetPostUseCase : KoinComponent {
    private val repository by inject<PostRepository>()

    suspend operator fun invoke(postId: Long): Result<Post> {
        return repository.getPost(postId = postId)
    }
}