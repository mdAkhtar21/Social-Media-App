package com.example.socialmedialapp.common.data.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class LikeParams(
    val postId: Long,
    val userId: Int
)

@Serializable
data class LikeResponseData(
    val success: Boolean,
    val message: String? = null
)

data class LikeApiResponse(
    val code: HttpStatusCode,
    val data: LikeResponseData
)