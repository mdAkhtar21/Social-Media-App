package com.example.socialmedialapp.common.data.local

import com.example.socialmedialapp.auth.domain.model.AuthResultData
import kotlinx.serialization.Serializable

@Serializable
data class UserSettings (
    val id:Int=-1,
    val name: String="",
    val avatar:String?=null,
    val bio: String="",
    val token:String="",
    val followerCount:Int=0,
    val followingCount:Int=0
)

fun UserSettings.toAuthResultData(): AuthResultData {
    return AuthResultData(
        id ,
        name,
        avatar,
        bio ,
        token,
        followerCount ,
        followingCount
    )
}

fun AuthResultData.toUserSettings(): UserSettings {
    return UserSettings(
        id ,
        name,
        avatar,
        bio,
        token,
        followerCount,
        followingCount
    )
}
