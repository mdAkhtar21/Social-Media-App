package com.example.socialmedialapp.android.account.profile

import androidx.compose.runtime.Composable
import com.example.socialmedialapp.android.destinations.EditProfileDestination
import com.example.socialmedialapp.android.destinations.FollowersDestination
import com.example.socialmedialapp.android.destinations.FollowingDestination
import com.example.socialmedialapp.android.account.edit.EditProfile
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.dsl.koinApplication


@Composable
@Destination
fun Profile(
    userId: Long,
    navigator: DestinationsNavigator
){
    val viewModel: ProfileViewModel = koinViewModel()

    ProfileScreen(
        userInfoUiState = viewModel.userInfoUiState,
        profilePostsUiState = viewModel.profilePostsUiState,
        profileId = userId,
        onUiAction = viewModel::onUiAction,
        onFollowButtonClick = {
            viewModel.userInfoUiState.profile?.let { profile ->
                if (profile.isOwnProfile) {
                    navigator.navigate(EditProfileDestination(userId))
                } else {
                    viewModel.onUiAction(ProfileUiAction.FollowUserAction(profile = profile))
                }
            }
        },
        onFollowersScreenNavigation = { navigator.navigate(FollowersDestination(userId)) },
        onFollowingScreenNavigation = { navigator.navigate(FollowingDestination(userId)) },
        onPostDetailNavigation = {}
    )
}