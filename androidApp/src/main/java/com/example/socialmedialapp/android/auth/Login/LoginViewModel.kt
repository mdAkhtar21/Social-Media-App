// LoginViewModel.kt
package com.example.socialmedialapp.android.auth.Login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedialapp.common.data.local.UserSettings
import com.example.socialmedialapp.common.data.local.toUserSettings
import com.example.socialmedialapp.auth.domain.usecase.SignInUsecase
import com.example.socialmedialapp.common.util.Result
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInUsecase: SignInUsecase,
    private val dataStore: DataStore<UserSettings>
): ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun signIn(){
        viewModelScope.launch {
            uiState=uiState.copy(isAuthenticating = true)
            val authResultData=signInUsecase(uiState.email,uiState.password)
            uiState=when(authResultData){
                is Result.Error->{
                    uiState.copy(
                        isAuthenticating = false,
                        authErrorMessage = authResultData.message
                    )
                }
                is Result.Success->{
                    uiState.copy(
                        isAuthenticating = false,
                        authenticationSucceed = true
                    )
                }
            }
        }
    }
    fun updateEmail(input: String) {
        uiState = uiState.copy(email = input)
    }

    fun updatePassword(input: String) {
        uiState = uiState.copy(password = input)
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isAuthenticating:Boolean=false,
    val authErrorMessage:String?=null,
    val authenticationSucceed:Boolean=false
)
