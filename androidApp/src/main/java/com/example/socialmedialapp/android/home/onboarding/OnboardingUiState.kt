package com.example.socialmedialapp.android.home.onboarding


import com.example.socialmedialapp.android.fake_data.SampleFollowsUser

data class OnboardingUiState(
    val isLoading:Boolean=false,
    val users:List<SampleFollowsUser> = listOf(),
    val errorMessage: String?=null,
    val shouldShowOnBoarding:Boolean=false
)