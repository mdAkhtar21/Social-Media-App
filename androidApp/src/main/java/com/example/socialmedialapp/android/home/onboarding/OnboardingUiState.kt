package com.example.socialmedialapp.android.home.onboarding


import com.example.socialmedialapp.android.common.fake_data.FollowsUser

data class OnBoardingUiState(
    val isLoading: Boolean = false,
    val users: List<FollowsUser> = listOf(),
    val errorMessage: String? = null,
    val shouldShowOnBoarding: Boolean = false
)