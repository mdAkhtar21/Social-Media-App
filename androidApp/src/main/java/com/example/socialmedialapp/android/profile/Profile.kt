package com.example.socialmedialapp.android.profile

import androidx.compose.runtime.Composable
import com.example.socialmedialapp.android.destinations.EditProfileDestination
import com.example.socialmedialapp.android.destinations.FollowersDestination
import com.example.socialmedialapp.android.destinations.FollowingDestination
import com.example.socialmedialapp.android.edit.EditProfile
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.dsl.koinApplication


@Composable
@Destination
fun Profile(
    userId:Int,
    navigator: DestinationsNavigator
){
    val viewModel:ProfileViewModel= koinViewModel()
    
    ProfileScreen(
        userInfoUiState = viewModel.userInfoUiState,
        profilePostsUiState = viewModel.profilePostsUiState,
        onButtonClick = {navigator.navigate(EditProfileDestination(userId))},
        onFollowerClick = {navigator.navigate(FollowersDestination(userId))},
        onFollowingClick ={navigator.navigate(FollowingDestination(userId))},
        onPostClick = {},
        onLikeClick = {},
        onCommentClick = {},
        fetchData = {viewModel.fetchProfile(userId)}
    )
}