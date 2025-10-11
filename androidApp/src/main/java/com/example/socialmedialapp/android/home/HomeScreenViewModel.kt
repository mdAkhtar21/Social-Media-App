package com.example.socialmedialapp.android.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedialapp.android.common.util.Constants
import com.example.socialmedialapp.android.common.util.DefaultPagingManager
import com.example.socialmedialapp.android.common.util.PagingManager
import com.example.socialmedialapp.common.domain.model.FollowsUser
import com.example.socialmedialapp.common.domain.model.Post
import com.example.socialmedialapp.common.util.Result
import com.example.socialmedialapp.follows.domain.usecase.FollowOrUnfollowUseCase
import com.example.socialmedialapp.follows.domain.usecase.GetFollowableUsersUseCase
import com.example.socialmedialapp.post.domain.usecase.GetPostsUseCase
import com.example.socialmedialapp.post.domain.usecase.LikeOrDislikePostUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getFollowableUsersUseCase: GetFollowableUsersUseCase,
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase,
    private val getPostsUseCase:GetPostsUseCase,
    private val likePostUseCase: LikeOrDislikePostUseCase
) : ViewModel() {

    var postsFeedUiState by mutableStateOf(PostsFeedUiState())
        private set

    var onboardingUiState by mutableStateOf(OnBoardingUiState())
        private set

    var homeRefreshState by mutableStateOf(HomeRefreshState())
        private set

    private val pagingManager by lazy{createPagingManager()}

    init {
        fetchData()
    }

   private fun fetchData() {
        homeRefreshState=homeRefreshState.copy(isRefreshing = true)

        viewModelScope.launch {
            delay(1000)

            val onboardingDeferred = async {
                getFollowableUsersUseCase()
            }

            pagingManager.apply {
                reset()
                loadItems()
            }
            handleOnBoardingResult(onboardingDeferred.await())
            homeRefreshState=homeRefreshState.copy(isRefreshing = false)
        }
    }

    private fun createPagingManager(): PagingManager<Post>{
        return DefaultPagingManager(
            onRequest = {page ->
                getPostsUseCase(page, Constants.DEFAULT_REQUEST_PAGE_SIZE)
            },
            onSuccess = {posts, page ->
                postsFeedUiState = if (posts.isEmpty()){
                    postsFeedUiState.copy(endReached = true)
                }else{
                    if (page == Constants.INITIAL_PAGE_NUMBER){
                        postsFeedUiState = postsFeedUiState.copy(posts = emptyList())
                    }
                    postsFeedUiState.copy(
                        posts = postsFeedUiState.posts + posts,
                        endReached = posts.size < Constants.DEFAULT_REQUEST_PAGE_SIZE
                    )
                }
            },
            onError = {cause, page ->
                if (page == Constants.INITIAL_PAGE_NUMBER){
                    homeRefreshState = homeRefreshState.copy(
                        refeshingErrorMessage  = cause
                    )
                }else{
                    postsFeedUiState = postsFeedUiState.copy(
                        loadingErrorMessage = cause
                    )
                }
            },
            onLoadStateChange = {isLoading ->
                postsFeedUiState = postsFeedUiState.copy(
                    isLoading = isLoading
                )
            }
        )
    }

    private fun loadMorePosts() {
        if (postsFeedUiState.endReached) return
        viewModelScope.launch {
            pagingManager.loadItems()
        }
    }

    private fun handleOnBoardingResult(result: Result<List<FollowsUser>>){
        when(result){
            is Result.Error ->Unit
            is Result.Success -> {
                result.data?.let {followsUsers ->
                    onboardingUiState=onboardingUiState.copy(
                        shouldShowOnboarding = followsUsers.isNotEmpty(),
                        followableUsers = followsUsers
                    )

                }
            }
        }
    }
    private fun followUser(followUser:FollowsUser){
        viewModelScope.launch {
            val result=followOrUnfollowUseCase(
                followedUserId = followUser.id,
                shouldFollow = !followUser.isFollowing
            )
            onboardingUiState=onboardingUiState.copy(
                followableUsers=onboardingUiState.followableUsers.map{
                    if(it.id==followUser.id){
                        it.copy(isFollowing=!followUser.isFollowing)
                    }else it
                }
            )
            when(result){
                is Result.Error -> {
                    onboardingUiState=onboardingUiState.copy(
                        followableUsers=onboardingUiState.followableUsers.map{
                            if(it.id==followUser.id){
                                it.copy(isFollowing = followUser.isFollowing)
                            }else it
                        }
                    )
                }
                is Result.Success -> Unit
            }
        }
    }

    private fun dismissOnboarding() {
        val hasFollowing = onboardingUiState.followableUsers.any { it.isFollowing }
        if (!hasFollowing) {
            // Do nothing or handle empty follow state
        } else {
            onboardingUiState = onboardingUiState.copy(
                shouldShowOnboarding = false,
                followableUsers = emptyList()
            )
            fetchData()
        }
    }


    private fun likeOrUnlikePost(post: Post) {
        viewModelScope.launch {
            val count = if (post.isLiked) -1 else +1
            postsFeedUiState = postsFeedUiState.copy(
                posts = postsFeedUiState.posts.map {
                    if (it.postId == post.postId) {
                        it.copy(
                            isLiked = !post.isLiked,
                            likesCount = post.likesCount.plus(count)
                        )
                    } else it
                }
            )

            val result = likePostUseCase(
                post = post,
            )

            when (result) {
                is Result.Error -> {
                    postsFeedUiState=postsFeedUiState.copy(
                        posts=postsFeedUiState.posts.map{
                            if(it.postId==post.postId) post else it
                        }
                    )
                }

                is Result.Success -> Unit
            }
        }
    }

    fun onUiAction(uiAction: HomeUiAction){
        when(uiAction){
            is HomeUiAction.FollowUserAction -> followUser(uiAction.user)
            HomeUiAction.LoadMorePostsAction -> loadMorePosts()
            is HomeUiAction.PostLikeAction -> likeOrUnlikePost(uiAction.post)
            HomeUiAction.RefreshingAction -> fetchData()
            HomeUiAction.RemoveOnBoardingAction ->dismissOnboarding()
        }
    }
}


data class HomeRefreshState(
    val isRefreshing: Boolean = false,
    val refeshingErrorMessage:String?=null
)

data class OnBoardingUiState(
    val shouldShowOnboarding: Boolean=false,
    val followableUsers :List<FollowsUser> = listOf(),
)


data class PostsFeedUiState(
    val isLoading:Boolean=false,
    val posts:List<Post> = listOf(),
    val loadingErrorMessage:String?=null,
    val endReached: Boolean = false
)

sealed interface HomeUiAction{
    data class FollowUserAction(val user:FollowsUser) :HomeUiAction
    data class PostLikeAction(val post:Post):HomeUiAction
    data object RemoveOnBoardingAction: HomeUiAction
    data object RefreshingAction : HomeUiAction
    data object LoadMorePostsAction :HomeUiAction
}