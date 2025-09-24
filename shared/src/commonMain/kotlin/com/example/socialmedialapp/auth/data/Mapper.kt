package com.example.socialmedialapp.auth.data

import com.example.socialmedialapp.auth.domain.model.AuthResultData

internal fun AuthResponseData.toAuthResultData(): AuthResultData {
    return AuthResultData(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar,
        token = token,
        followerCount = followerCount,
        followingCount = followingCount
    )
}
