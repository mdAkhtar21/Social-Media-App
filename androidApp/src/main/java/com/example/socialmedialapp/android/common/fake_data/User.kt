package com.example.socialmedialapp.android.common.fake_data

data class SampleFollowsUser(
    val id: Int,
    val name: String,
    val bio: String = "Hey there, welcome to my social app page!",
    val profileUrl: String,
    val isFollowing: Boolean = false
)

val sampleUsers = listOf(
    SampleFollowsUser(
        id = 1,
        name = "Mr Dip",
        profileUrl = "https://picsum.photos/200"
    ),
    SampleFollowsUser(
        id = 2,
        name = "John Cena",
        profileUrl = "https://picsum.photos/200"
    ),
    SampleFollowsUser(
        id = 3,
        name = "Cristiano",
        profileUrl = "https://picsum.photos/200"
    ),
    SampleFollowsUser(
        id = 4,
        name = "L. James",
        profileUrl = "https://picsum.photos/200"
    )
)