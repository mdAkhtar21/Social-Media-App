package com.example.socialmedialapp.auth.domain.model

data class AuthResultData(
    val id:Int,
    val name: String,
    val avatar:String?=null,
    val bio: String,
    val token:String,
    val followerCount:Int=0,
    val followingCount:Int=0
)