package com.example.socialmedialapp.android.post

import androidx.compose.runtime.Composable
import com.example.socialmedialapp.android.common.fake_data.Post
import com.example.socialmedialapp.android.destinations.ProfileDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.dsl.koinApplication

@Composable
@Destination
fun PostDetail(
    navigator: DestinationsNavigator,
    postId: Long
) {
    val viewModel: PostDetailViewModel = koinViewModel()

    PostDetailScreen(
        postUiState = viewModel.postUiState,
        commentsUiState = viewModel.commentsUiState,
        postId = postId,
        onProfileNavigation = {
            navigator.navigate(ProfileDestination(it))
        },
        onUiAction = viewModel::onUiAction
    )
}