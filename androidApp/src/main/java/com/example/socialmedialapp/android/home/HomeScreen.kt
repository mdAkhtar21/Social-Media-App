package com.example.socialmedialapp.android.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialmedialapp.android.common.components.PostListItem
import com.example.socialmedialapp.android.common.theming.SocialAppTheme
import com.example.socialmedialapp.android.common.fake_data.Post
import com.example.socialmedialapp.android.common.fake_data.SampleFollowsUser
import com.example.socialmedialapp.android.common.fake_data.samplePosts
import com.example.socialmedialapp.android.common.fake_data.sampleUsers
import com.example.socialmedialapp.android.home.onboarding.OnBoardingSelection
import com.example.socialmedialapp.android.home.onboarding.OnboardingUiState
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun HomeScreen(
    modifier: Modifier = Modifier,
    onboardingUiState: OnboardingUiState,
    postUiState: PostUiState,
    onPostClick: (Post) -> Unit,
    onProfileClick: (Int) -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onFollowButtonClick: (Boolean, SampleFollowsUser) -> Unit,
    onboardingFinish: () -> Unit,
    fetchData: () -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()
    LaunchedEffect(onboardingUiState.isLoading, postUiState.isLoading) {
        if (onboardingUiState.isLoading || postUiState.isLoading) {
            pullRefreshState.startRefresh()
        } else {
            pullRefreshState.endRefresh()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            // 3. Use the correct modifier pullToRefresh with the state and onRefresh callback.
//            .pullRefreshState(state = pullRefreshState, onRefresh = { fetchData() })
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (onboardingUiState.shouldShowOnBoarding) {
                item {
                    OnBoardingSelection(
                        users = onboardingUiState.users,
                        onUserClick = { user-> onProfileClick(user.id) },
                        onFollowButtonClick = onFollowButtonClick
                    ) {
                        onboardingFinish()
                    }
                }
            }

            items(
                items = postUiState.post,
                key = { post -> post.id }
            ) { post ->
                PostListItem(
                    post = post,
                    onPostClick = onPostClick,
                    onProfileClick = onProfileClick,
                    onLikeClick = onLikeClick,
                    onCommentClick = onCommentClick
                )
            }
        }
        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(
                onboardingUiState = OnboardingUiState(users = sampleUsers, shouldShowOnBoarding = true),
                postUiState = PostUiState(post = samplePosts),
                onFollowButtonClick = { _, _ -> },
                onPostClick = {},
                onProfileClick = {},
                onLikeClick = {},
                onCommentClick = {},
                fetchData = {},
                onboardingFinish = {}
            )
        }
    }
}