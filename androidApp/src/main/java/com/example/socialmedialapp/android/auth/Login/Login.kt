// Login.kt
package com.example.socialmedialapp.android.auth.Login

import androidx.compose.runtime.Composable
import com.example.socialmedialapp.android.auth.signUp.SignUp
import com.example.socialmedialapp.android.destinations.HomeDestination
import com.example.socialmedialapp.android.destinations.HomeScreenDestination
import com.example.socialmedialapp.android.destinations.LoginDestination
import com.example.socialmedialapp.android.destinations.SignUpDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun Login(
    navigator: DestinationsNavigator
) {
    val viewModel: LoginViewModel = koinViewModel()
    LoginScreen(
        uiState = viewModel.uiState,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onSignInClick = viewModel::signIn,
        onNavigateToHome = {
            navigator.navigate(HomeDestination){
                popUpTo(LoginDestination.route){inclusive = true}
            }
        },
        onNavigateToSignup = {
            navigator.navigate(SignUpDestination.route){
                popUpTo(LoginDestination.route){
                    inclusive = true
                }
            }
        }
    )
}