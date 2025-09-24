package com.example.socialmedialapp.android.home.onboarding

import com.example.socialmedialapp.android.fake_data.FollowsUser

data class OnboardingUiState(
    val isLoading:Boolean=false,
    val users:List<FollowsUser> = listOf(),
    val errorMessage: String?=null,
    val shouldShowOnBoarding:Boolean=false
)