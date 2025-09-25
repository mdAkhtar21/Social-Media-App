package com.example.socialmedialapp.android.post

import androidx.compose.runtime.Composable
import com.example.socialmedialapp.android.fake_data.Post
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.dsl.koinApplication

@Composable
@Destination
fun PostDetail(
    navigator: DestinationsNavigator,
    postId:String
) {
    val viewModel:PostDetailScreenViewModel= koinViewModel()

    PostDetailScreen(
        postUiState = viewModel.postUiState,
        commentsUiState = viewModel.commentsUiState,
        onCommentsMoreIconClick = {},
        onProfileClick = {},
        onAddCommentClick = {},
        fetchData ={viewModel.fetchData(postId)}
    )

}