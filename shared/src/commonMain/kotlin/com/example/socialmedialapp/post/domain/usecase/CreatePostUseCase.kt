package com.example.socialmedialapp.post.domain.usecase

import com.example.socialmedialapp.common.domain.model.Post
import com.example.socialmedialapp.common.util.Constants
import com.example.socialmedialapp.post.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.example.socialmedialapp.common.util.Result

class CreatePostUseCase: KoinComponent {
    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        caption: String,
        imageBytes: ByteArray
    ): Result<Post>{
        with(caption){
            if (isBlank() || length > 200){
                return Result.Error(message = Constants.INVALID_INPUT_POST_CAPTION_MESSAGE)
            }
        }
        return repository.createPost(caption = caption, imageBytes = imageBytes)
    }
}