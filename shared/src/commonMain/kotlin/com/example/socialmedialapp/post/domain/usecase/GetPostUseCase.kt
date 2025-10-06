package com.example.socialmedialapp.post.domain.usecase

import com.example.socialmedialapp.common.domain.model.Post
import com.example.socialmedialapp.common.util.Result
import com.example.socialmedialapp.post.domain.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetPostsUseCase: KoinComponent {
    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        page: Int,
        pageSize: Int
    ): Result<List<Post>> {
        return repository.getFeedPosts(page, pageSize)
    }
}