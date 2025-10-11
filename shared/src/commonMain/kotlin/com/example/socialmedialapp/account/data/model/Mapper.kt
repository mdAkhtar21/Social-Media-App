package com.example.socialmedialapp.account.data.model

import com.example.socialmedialapp.account.domain.model.Profile
import com.example.socialmedialapp.common.data.local.UserSettings

fun UserSettings.toDomainProfile(): Profile {
    return Profile(
        id = id.toLong(),
        name = name,
        bio = bio,
        imageUrl = avatar,
        followersCount = followerCount,
        followingCount = followingCount,
        isFollowing = false,
        isOwnProfile = true
    )
}

fun Profile.toUserSettings(token: String): UserSettings{
    return UserSettings(
        id = id.toInt(),
        name = name,
        bio = bio,
        avatar = imageUrl,
        followerCount = followersCount,
        followingCount = followingCount,
        token = token
    )
}