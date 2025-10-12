package com.example.socialmedialapp.auth.data

import com.example.socialmedialapp.auth.domain.model.AuthResultData

internal fun AuthResponseData.toAuthResultData(): AuthResultData{
    return AuthResultData(id, name, bio, avatar, token, followersCount, followingCount)
}
