// SignUp.kt
package com.example.socialmedialapp.android.auth.signUp

import androidx.compose.runtime.Composable
import com.example.socialmedialapp.android.destinations.HomeScreenDestination
import com.example.socialmedialapp.android.destinations.LoginDestination
import com.example.socialmedialapp.android.destinations.SignUpDestination
import com.example.socialmedialapp.android.home.HomeScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel


@Destination
@Composable
fun SignUp(
    navigator: DestinationsNavigator
) {
    val viewModel: SignUpViewModel = koinViewModel()
    SignUpScreen(
        uiState = viewModel.uiState,
        onUserNameChange = viewModel::updateUserName,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,

        onNavigateToHome = {
            navigator.navigate(HomeScreenDestination){
                popUpTo(SignUpDestination.route){
                    inclusive=true
                }
            }
        },
        onNavigateToLogin={
            navigator.navigate(LoginDestination){
                popUpTo(SignUpDestination.route){
                    inclusive=true
                }
            }
        },
        onSignUpClick = viewModel::signUp
    )
}
