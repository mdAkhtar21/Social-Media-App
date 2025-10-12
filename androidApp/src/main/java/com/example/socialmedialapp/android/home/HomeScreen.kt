package com.example.socialmedialapp.android.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialmedialapp.android.R
import com.example.socialmedialapp.android.common.components.PostListItem
import com.example.socialmedialapp.common.domain.model.Post
import com.example.socialmedialapp.android.common.theming.LargeSpacing
import com.example.socialmedialapp.android.common.theming.MediumSpacing
import com.example.socialmedialapp.android.common.theming.SocialAppTheme
import com.example.socialmedialapp.android.common.util.Constants
import com.example.socialmedialapp.android.home.onboarding.OnBoardingSection
import com.ramcosta.composedestinations.annotation.Destination

import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState



@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun HomeScreen(
    modifier: Modifier = Modifier,
    onBoardingUiState: OnBoardingUiState,
    postsFeedUiState: PostsFeedUiState,
    homeRefreshState: HomeRefreshState,
    onUiAction: (HomeUiAction) -> Unit,
    onProfileNavigation: (userId: Long) -> Unit,
    onPostDetailNavigation: (Post) -> Unit
)  {
//    val pullRefreshState = rememberPullRefreshState(
//        refreshing = homeRefreshState.isRefreshing,
//        onRefresh = { onUiAction(HomeUiAction.RefreshingAction) }
//    )
    val listState = rememberLazyListState()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = homeRefreshState.isRefreshing)
    val shouldFetchMorePosts by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo

            if (layoutInfo.totalItemsCount == 0) {
                false
            }else{
                val lastVisibleItem = visibleItemsInfo.last()
                (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount)
            }
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onUiAction(HomeUiAction.RefreshAction) },
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (onBoardingUiState.shouldShowOnBoarding) {
                item {
                    OnBoardingSection(
                        users = onBoardingUiState.followableUsers,
                        onUserClick = { user ->
                            val post = postsFeedUiState.posts.firstOrNull { it.userId == user.id }
                            post?.let { onPostDetailNavigation(it) }
                        },
                        onFollowButtonClick = {_,user->
                            onUiAction(
                                HomeUiAction.FollowUserAction(
                                    user
                                )
                            )
                        },
                        onBoardingFinish ={onUiAction(HomeUiAction.RemoveOnboardingAction)}
                    )

                    Text(
                        text = stringResource(id = R.string.onboarding_title),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = LargeSpacing),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(
                items = postsFeedUiState.posts,
                key = { post -> post.postId }
            ) {post->
                PostListItem(
                    post = post,
                    onPostClick = { onPostDetailNavigation(it) },
                    onProfileClick = { onProfileNavigation(it) },
                    onLikeClick = { onUiAction(HomeUiAction.PostLikeAction(it)) },
                    onCommentClick = { onPostDetailNavigation(it) }
                )
            }
            if(postsFeedUiState.isLoading && postsFeedUiState.posts.isEmpty()){
                item(key = Constants.LOADING_MORE_ITEM_KEY) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = MediumSpacing, horizontal = LargeSpacing),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

//        PullToRefreshContainer(
//            state = pullRefreshState,
//            modifier = Modifier.align(Alignment.TopCenter)
//        )

        LaunchedEffect(key1 = shouldFetchMorePosts) {
            if (shouldFetchMorePosts && !postsFeedUiState.endReached) {
                onUiAction(HomeUiAction.LoadMorePostsAction)
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(
                onBoardingUiState = OnBoardingUiState(),
                postsFeedUiState = PostsFeedUiState(),
                homeRefreshState = HomeRefreshState(),
                onPostDetailNavigation = {},
                onProfileNavigation = {},
                onUiAction = {},

                )
        }
    }
}