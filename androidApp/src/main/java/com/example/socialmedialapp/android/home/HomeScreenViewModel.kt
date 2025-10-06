package com.example.socialmedialapp.android.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedialapp.android.common.fake_data.Post
import com.example.socialmedialapp.android.common.fake_data.samplePosts
import com.example.socialmedialapp.android.common.fake_data.sampleUsers
import com.example.socialmedialapp.android.home.onboarding.OnboardingUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

    var postUiState by mutableStateOf(PostUiState()) // ✅ create an instance
        private set

    var onboardingUiState by mutableStateOf(OnboardingUiState()) // ✅ create an instance
        private set

    init {
        fetchData()
    }

    fun fetchData() {
        onboardingUiState = onboardingUiState.copy(isLoading = true)
        postUiState = postUiState.copy(isLoading = true)

        viewModelScope.launch {
            delay(1000)

            onboardingUiState = onboardingUiState.copy(
                isLoading = false,
                users = sampleUsers,
                shouldShowOnBoarding = true
            )

            postUiState = postUiState.copy(
                isLoading = false,
                post = samplePosts
            )
        }
    }
}


data class PostUiState(
    val isLoading:Boolean=false,
    val post:List<Post> =listOf(),
    val errorMessage:String?=null
)