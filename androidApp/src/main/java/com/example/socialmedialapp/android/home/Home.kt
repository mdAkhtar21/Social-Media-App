package com.example.socialmedialapp.android.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination(start = true)
fun Home (
    navigator:DestinationsNavigator
){
    val viewModel : HomeScreenViewModel= koinViewModel()

    HomeScreen(
        modifier = Modifier,
        onboardingUiState = viewModel.onboardingUiState,
        postUiState = viewModel.postUiState,
        onPostClick = {},
        onProfileClick ={},
        onLikeClick = {},
        onCommentClick = {},
        onFollowButtonClick = {_,_->},
        onboardingFinish = { TODO() },
        fetchData = {
            viewModel.fetchData()
        }
    )
}